package org.mp2.translator.auth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.mp2.translator.api.ErrorResponse;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Basic ")) {
            abort(requestContext, "Missing Basic Authorization header");
            return;
        }

        String encodedCredentials = authorization.substring("Basic ".length()).trim();
        String decoded = new String(Base64.getDecoder().decode(encodedCredentials), StandardCharsets.UTF_8);
        String[] parts = decoded.split(":", 2);
        if (parts.length < 2 || !AuthConfig.isValid(parts[0], parts[1])) {
            abort(requestContext, "Invalid credentials");
        }
    }

    private void abort(ContainerRequestContext context, String message) {
        context.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"DarijaTranslator\"")
                .entity(new ErrorResponse(message))
                .build());
    }
}
