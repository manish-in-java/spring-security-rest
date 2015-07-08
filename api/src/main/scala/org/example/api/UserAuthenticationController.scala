package org.example.api

import javax.servlet.http.{ HttpServletRequest, HttpServletResponse }
import org.example.transfer.Response
import org.springframework.beans.factory.annotation.{ Autowired, Qualifier }
import org.springframework.security.authentication.{ AuthenticationManager, InternalAuthenticationServiceException, UsernamePasswordAuthenticationToken }
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.web.bind.annotation.{ RequestMapping, RestController, RequestParam, RequestMethod }
import scala.beans.BeanProperty

/**
 * User Authentication controller.
 */
@RestController
class UserAuthenticationController {
  @Autowired
  @Qualifier("authenticationManager")
  var authenticationManager: AuthenticationManager = _
  @Autowired
  var securityContextRepository: SecurityContextRepository = _

  /**
   * Authenticates an API user using username and password information included in API call headers.
   *
   * @param request The [[HttpServletRequest]] for the API call.
   * @param response The [[HttpServletResponse]] for the API call.
   */
  @RequestMapping(method = Array(RequestMethod.POST), value = Array("/authenticate"))
  def authenticate(@RequestParam username: String, @RequestParam password: String, request: HttpServletRequest, response: HttpServletResponse): UserAuthenticationResponse = {
    val authenticationResponse = new UserAuthenticationResponse(username)

    // Create an authentication request.
    val authenticationRequest = new UsernamePasswordAuthenticationToken(username, password)

    try {
      // Attempt authentication using the Spring Security configuration for the application.
      val authenticationResult = this.authenticationManager.authenticate(authenticationRequest)

      if (authenticationResult == null) {
        // Return immediately as authentication couldn't be completed.
        authenticationResponse.addError("Unable to authenticate the user.")
      } else {
        // Add an authentication token to the response.
         println("%% " + authenticationResult.getDetails)
         authenticationResponse.token = authenticationResult.getDetails.asInstanceOf[String]

        // Save the authentication token in the security context.
        SecurityContextHolder.getContext.setAuthentication(authenticationResult)

        // Save the authentication for future requests.
        this.securityContextRepository.saveContext(SecurityContextHolder.getContext, request, response)
      }
    } catch {
      case failed: InternalAuthenticationServiceException => {
        SecurityContextHolder.clearContext()

        authenticationResponse.addError("Unable to authenticate the user.")
      }
      case failed: AuthenticationException => {
        SecurityContextHolder.clearContext()

        authenticationResponse.addError("User authentication failed.  Unknown username or incorrect password.")
      }
    }

    authenticationResponse
  }
}

/**
 * Represents an API authentication response.
 */
class UserAuthenticationResponse(@transient val username: String) extends Response {
  @BeanProperty
  var token: String = _
}
