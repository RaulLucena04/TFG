package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import util.Config;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

/**
 * Cliente TCP para comunicarse con el backend por sockets.
 *
 * <p>Implementa un protocolo simple de frames JSON con prefijo de longitud (int32),
 * enviando {@code action} + {@code payload} y devolviendo {@code data} o lanzando
 * excepción con el mensaje de {@code error}.</p>
 */
public class SocketApiClient {
    private final ObjectMapper mapper;

    public SocketApiClient() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public <T> T request(String action, Object payload, Class<T> dataClass) throws Exception {
        JsonNode data = requestRaw(action, payload);
        return mapper.convertValue(data, dataClass);
    }

    public <T> T request(String action, Object payload, TypeReference<T> typeRef) throws Exception {
        JsonNode data = requestRaw(action, payload);
        return mapper.convertValue(data, typeRef);
    }

    private JsonNode requestRaw(String action, Object payload) throws Exception {
        String requestId = UUID.randomUUID().toString();
        Map<String, Object> req = Map.of(
                "requestId", requestId,
                "action", action,
                "payload", payload
        );

        String host = Config.getServerHost();
        int port = Config.getServerPort();

        try (Socket socket = new Socket(host, port);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            writeFrame(out, mapper.writeValueAsString(req));
            String respJson = readFrame(in);
            if (respJson == null) {
                throw new RuntimeException("Sin respuesta del servidor");
            }

            JsonNode resp = mapper.readTree(respJson);
            boolean ok = resp.path("ok").asBoolean(false);
            if (!ok) {
                String err = resp.path("error").asText("Error desconocido");
                throw new RuntimeException(err);
            }
            return resp.get("data");
        }
    }

    private static void writeFrame(DataOutputStream out, String json) throws Exception {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        out.writeInt(bytes.length);
        out.write(bytes);
        out.flush();
    }

    private static String readFrame(DataInputStream in) throws Exception {
        int len = in.readInt();
        if (len < 0 || len > 50_000_000) {
            throw new RuntimeException("Longitud de frame inválida: " + len);
        }
        byte[] buf = new byte[len];
        in.readFully(buf);
        return new String(buf, StandardCharsets.UTF_8);
    }
}

