package net.kaiba;

import io.quarkus.arc.Arc;
import io.quarkus.oidc.UserInfo;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

@Path("/live")
public class LiveResource {
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

    @Path("/test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUserInfo() {
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
        return "ok";
    }

}
