package org.example.web.security

import org.example.service.UserService
import org.example.transfer.UserAuthenticationRequest
import org.example.transfer.UserAuthenticationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder

/**
 * An implementation of Spring Security {@link AuthenticationProvider} that uses
 * a username and a password for user authentication.
 */
class UsernamePasswordAuthenticationProvider extends AuthenticationProvider {
  @Autowired
  var userService: UserService = _

  /**
   * Processes login information and attempts to authenticate the user.
   */
  override def authenticate(authentication: Authentication): Authentication = {
    val authenticationResponse = this.userService.authenticate(UserAuthenticationRequest(authentication.getName, authentication.getCredentials.asInstanceOf[String]))

    if (authenticationResponse.isSuccess) {
      SecurityContextHolder.getContext.setAuthentication(WebAuthenticationToken(authenticationResponse))

      return SecurityContextHolder.getContext.getAuthentication
    }

    throw new AuthenticationServiceException(String.format("The username [%s] could not be authenticated.", authentication.getName))
  }

  /**
   * Indicates that authentication using a username and password is
   * supported.
   */
  override def supports(authentication: Class[_]) = authentication != null && authentication == classOf[UsernamePasswordAuthenticationToken]
}
