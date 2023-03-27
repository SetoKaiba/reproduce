package net.kaiba;

import io.quarkus.arc.Arc;
import io.quarkus.oidc.UserInfo;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

@ServerEndpoint(value = "/liveWebSocket/{roomId}", configurator = WebSocketSecurityConfigurator.class)
public class LiveServerEndpoint {
    @Inject
    Logger logger;
    @Inject
    SecurityIdentity securityIdentity;
    @Inject
    UserInfo userInfo;
    @Inject
    JsonWebToken jsonWebToken;
    @Inject
    ManagedExecutor managedExecutor;

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId) {
        logger.infof("onOpen: %s, %s", session, roomId);
        logger.info(securityIdentity.isAnonymous());
        logger.info(securityIdentity.getPrincipal());
        logger.info(userInfo);
        logger.info(jsonWebToken);
        logger.info(Arc.container().requestContext().isActive());
        managedExecutor.submit(() -> {
            logger.info("sub:" + securityIdentity.isAnonymous());
            logger.info("sub:" + securityIdentity.getPrincipal());
            logger.info("sub:" + userInfo);
            logger.info("sub:" + jsonWebToken);
            logger.info("sub:" + Arc.container().requestContext().isActive());
        });
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason, @PathParam("roomId") String roomId) {
        logger.infof("onClose: %s, %s, %s", session, closeReason, roomId);
    }

    @OnError
    public void onError(Session session, Throwable throwable, @PathParam("roomId") String roomId) {
        logger.errorf(throwable, "onError: %s, %s", session, roomId);
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("roomId") String roomId) {
        logger.infof("onMessage: %s, %s, %s", session, message, roomId);
        logger.info(userInfo);
        logger.info(jsonWebToken);
    }
}
