package net.kaiba;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

import java.util.List;

public class WebSocketSecurityConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        if (request.getHeaders().get("Sec-WebSocket-Protocol") != null) {
            String subProtocol = request.getHeaders().get("Sec-WebSocket-Protocol").get(0).split(",")[0];
            response.getHeaders().put("sec-websocket-protocol", List.of(subProtocol));
        }
    }
}
