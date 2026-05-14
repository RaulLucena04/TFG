package com.tfg.nbabackend.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Servidor TCP para el backend "por sockets" (no WebSocket / no Socket.IO).
 *
 * <p>Características importantes del diseño:</p>
 * <ul>
 *   <li><b>Request/response</b>: cada mensaje del cliente recibe exactamente una respuesta.</li>
 *   <li><b>Sin push</b>: el servidor no mantiene un canal de notificaciones asíncronas a clientes.</li>
 *   <li><b>Frames length-prefixed</b>: {@link SocketProtocol} define el encuadre (int32 + JSON UTF-8).</li>
 * </ul>
 *
 * <p>Implicación práctica: si el servidor actualiza estado derivado (p.ej. puntos tras finalizar partido),
 * los clientes deben refrescar (p.ej. {@code user.get}/{@code user.list}) si quieren verlo inmediatamente.</p>
 *
 * <h2>Flujo (paso a paso)</h2>
 * <ol>
 *   <li>Spring Boot arranca y ejecuta {@link #run(String...)} (por {@link CommandLineRunner}).</li>
 *   <li>Se abre un {@link ServerSocket} en {@code socket.bindAddress}:{@code socket.port}.</li>
 *   <li>Un hilo dedicado ({@code socket-accept-loop}) hace {@code accept()} en bucle.</li>
 *   <li>Cada conexión entrante se procesa en el {@link #clientPool}.</li>
 *   <li>Dentro de cada conexión, se leen frames: {@link SocketProtocol#readFrame(DataInputStream)}.</li>
 *   <li>Se parsea JSON a {@link SocketRequest}, se routea con {@link SocketDispatcher} y se responde.</li>
 * </ol>
 *
 * <h2>Concurrencia</h2>
 * <ul>
 *   <li>Cada cliente se atiende en un hilo del pool (modelo clásico "thread-per-connection").</li>
 *   <li>Dentro de una misma conexión, las peticiones se procesan secuencialmente (un while).</li>
 * </ul>
 */
@Component
public class SocketServerRunner implements CommandLineRunner, DisposableBean {
    private final ObjectMapper mapper;
    private final SocketDispatcher dispatcher;

    private final String bindAddress;
    private final int port;

    private volatile ServerSocket serverSocket;
    private final ExecutorService clientPool = Executors.newCachedThreadPool();

    public SocketServerRunner(
            ObjectMapper mapper,
            SocketDispatcher dispatcher,
            @Value("${socket.bindAddress:0.0.0.0}") String bindAddress,
            @Value("${socket.port:9090}") int port
    ) {
        this.mapper = mapper;
        this.dispatcher = dispatcher;
        this.bindAddress = bindAddress;
        this.port = port;
    }

    @Override
    public void run(String... args) throws Exception {
        InetAddress bind = InetAddress.getByName(bindAddress);
        serverSocket = new ServerSocket(port, 50, bind);
        Thread acceptThread = new Thread(this::acceptLoop, "socket-accept-loop");
        acceptThread.setDaemon(false);
        acceptThread.start();

        System.out.println("Socket backend escuchando en " + bindAddress + ":" + port);
    }

    private void acceptLoop() {
        while (serverSocket != null && !serverSocket.isClosed()) {
            try {
                Socket client = serverSocket.accept();
                // Delegamos el procesamiento del cliente a un hilo del pool.
                clientPool.submit(() -> handleClient(client));
            } catch (Exception e) {
                if (serverSocket == null || serverSocket.isClosed()) {
                    return;
                }
                System.err.println("Error aceptando cliente: " + e.getMessage());
            }
        }
    }

    private void handleClient(Socket client) {
        try (Socket c = client;
             DataInputStream in = new DataInputStream(c.getInputStream());
             DataOutputStream out = new DataOutputStream(c.getOutputStream())) {

            while (true) {
                // Bloquea hasta recibir un frame completo. Si el cliente cierra conexión, devuelve null.
                String json = SocketProtocol.readFrame(in);
                if (json == null) {
                    return;
                }

                SocketRequest req;
                try {
                    req = mapper.readValue(json, SocketRequest.class);
                } catch (Exception parse) {
                    // Si el JSON es inválido, respondemos con ok=false pero mantenemos la conexión abierta,
                    // para permitir que el cliente reintente sin reconectar.
                    SocketProtocol.writeFrame(out,
                            mapper.writeValueAsString(SocketResponse.error(null, "JSON inválido")));
                    continue;
                }

                // Dispatch 1:1: exactamente una respuesta por petición.
                SocketResponse resp = dispatcher.handle(req);
                SocketProtocol.writeFrame(out, mapper.writeValueAsString(resp));
            }
        } catch (Exception e) {
            System.err.println("Cliente desconectado/error: " + e.getMessage());
        }
    }

    @Override
    public void destroy() throws Exception {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        // Cortamos hilos del pool al apagar Spring, para evitar que se quede el proceso vivo.
        clientPool.shutdownNow();
    }
}

