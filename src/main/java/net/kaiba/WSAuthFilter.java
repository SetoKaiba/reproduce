package net.kaiba;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;

import java.util.Arrays;
import java.util.List;

public class WSAuthFilter {
    @RouteFilter(401)
    void addAuthHeader(RoutingContext rc) {
        try {
            String secWebSocketProtocol = rc.request().headers().get("Sec-WebSocket-Protocol");
            if (secWebSocketProtocol != null) {
                List<String> protocols = Arrays.asList(secWebSocketProtocol.split(","));
                for (int i = 0; i < protocols.size(); i++) {
                    if (protocols.get(i).equals("Bearer") && !protocols.get(i + 1).equals(" undefined")) {
                        rc.request().headers().add("Authorization", new StringBuilder().append("Bearer").append(protocols.get(i + 1)));
                    }
                }
            }
        } finally {
            rc.next();
        }
    }
}
