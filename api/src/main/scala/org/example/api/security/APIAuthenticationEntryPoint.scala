package org.example.api.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

import javax.servlet.http.{ HttpServletRequest, HttpServletResponse }

/**
 * Denies all unauthenticated API requests.
 */
class APIAuthenticationEntryPoint extends AuthenticationEntryPoint {
  /**
   * {@inheritDoc}
   */
  override def commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token was either missing or invalid.")
  }
}
