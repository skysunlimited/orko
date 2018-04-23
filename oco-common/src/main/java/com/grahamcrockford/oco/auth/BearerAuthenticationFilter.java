package com.grahamcrockford.oco.auth;

import java.io.IOException;
import java.util.Optional;

import javax.annotation.Priority;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.auth.AuthenticationException;

/**
 * Container-level filter which performs JWT verification.  We do it this way so that it'll
 * work for Jersey, Websockets, the admin interface and any static assets. This is the lowest
 * common denominator.
 */
@Singleton
@Priority(102)
class BearerAuthenticationFilter extends AbstractHttpSecurityServletFilter {

  static final String AUTHORIZATION = "authorization";

  private static final Logger LOGGER = LoggerFactory.getLogger(BearerAuthenticationFilter.class);

  private final OcoAuthenticator authenticator;
  private final OcoAuthorizer authorizer;


  @Inject
  BearerAuthenticationFilter(OcoAuthenticator authenticator, OcoAuthorizer authorizer) {
    this.authenticator = authenticator;
    this.authorizer = authorizer;
  }

  @Override
  protected boolean filterHttpRequest(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

    String authorization = request.getHeader(AUTHORIZATION);
    if (authorization == null || !authorization.startsWith("Bearer ") || authorization.length() <= 7) {
      LOGGER.error(request.getPathInfo() + ": invalid auth header: " + authorization + " on " + request.getContextPath() + request.getServletPath());
      response.sendError(401);
      return false;
    }

    String accessToken = authorization.substring(7);

    try {
      Optional<AccessTokenPrincipal> principal = authenticator.authenticate(accessToken);
      if (!principal.isPresent()) {
        LOGGER.error(request.getPathInfo() + ": Unauthorised login attempt");
        response.sendError(401);
        return false;
      }
      if (!authorizer.authorize(principal.get(), Roles.TRADER)) {
        LOGGER.error(request.getPathInfo() + ": user [{}] not authorised", principal.get().getName());
        response.sendError(401);
        return false;
      }
    } catch (AuthenticationException e) {
      LOGGER.error(request.getPathInfo() + ": invalid token", e);
      response.sendError(401);
      return false;
    }

    return true;
  }

}
