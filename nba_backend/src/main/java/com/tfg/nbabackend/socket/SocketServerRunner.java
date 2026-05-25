package com.tfg.nbabackend.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Servidor TCP: abre el {@link ServerSocket}, acepta conexiones y las delega al pool.
 *
 * <p>El formato binario de cada mensaje lo serializa {@link SocketFrameSerializer}. El protocolo de
 * aplicación (acciones, payloads) está en {@link SocketDispatcher}.</p>
 *
 * <ul>
 *   <li><b>Request/response</b>: cada petición del cliente recibe una respuesta (la implementa {@link SocketClientHandler}).</li>
 *   <li><b>Sin push</b>: no hay eventos servidor→cliente fuera de esa respuesta.</li>
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
                clientPool.submit(new SocketClientHandler(client, mapper, dispatcher));
            } catch (Exception e) {
                if (serverSocket == null || serverSocket.isClosed()) {
                    return;
                }
                System.err.println("Error aceptando cliente: " + e.getMessage());
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        clientPool.shutdownNow();
    }
}
