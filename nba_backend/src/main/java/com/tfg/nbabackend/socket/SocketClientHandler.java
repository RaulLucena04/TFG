package com.tfg.nbabackend.socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Atiende una conexión TCP ya aceptada: bucle request/response hasta que el cliente cierre.
 *
 * <p>La responsabilidad de {@link SocketServerRunner} es solo {@code accept()}, delegar en un pool
 * y cerrar el {@link java.net.ServerSocket}; el procesamiento por conexión vive aquí.</p>
 */
public final class SocketClientHandler implements Runnable {

    private final Socket client;
    private final ObjectMapper mapper;
    private final SocketDispatcher dispatcher;

    public SocketClientHandler(Socket client, ObjectMapper mapper, SocketDispatcher dispatcher) {
        this.client = client;
        this.mapper = mapper;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        try (Socket c = client;
             DataInputStream in = new DataInputStream(c.getInputStream());
             DataOutputStream out = new DataOutputStream(c.getOutputStream())) {

            while (true) {
                String json = SocketFrameSerializer.readFrame(in);
                if (json == null) {
                    return;
                }

                SocketRequest req;
                try {
                    req = mapper.readValue(json, SocketRequest.class);
                } catch (Exception parse) {
                    SocketFrameSerializer.writeFrame(out,
                            mapper.writeValueAsString(SocketResponse.error(null, "JSON inválido")));
                    continue;
                }

                SocketResponse resp = dispatcher.handle(req);
                SocketFrameSerializer.writeFrame(out, mapper.writeValueAsString(resp));
            }
        } catch (Exception e) {
            System.err.println("Cliente desconectado/error: " + e.getMessage());
        }
    }
}
