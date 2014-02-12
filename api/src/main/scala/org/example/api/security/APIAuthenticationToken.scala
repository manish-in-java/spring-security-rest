package org.example.api.security;

import java.util.{ Collection, HashSet, Set }

import org.example.security.TokenGenerator
import org.example.transfer.{ UserAuthenticationResponse, UserRoleEnum }
import org.springframework.security.core.{ Authentication, CredentialsContainer, GrantedAuthority }
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * A Spring Security {@link Authentication} implementation that stores
 * authentication and authorization information for a person.
 */
final class APIAuthenticationToken(user: UserAuthenticationResponse) extends Authentication with CredentialsContainer {
  private val authorities: Set[GrantedAuthority] = new HashSet[GrantedAuthority](1)
  authorities.add(new SimpleGrantedAuthority(user.role.name))
  private val name = user.name
  private val principal = user.username
  private val role = user.role
  private val token = TokenGenerator.generateToken

  /**
   * @see org.springframework.security.core.CredentialsContainer#eraseCredentials()
   */
  override def eraseCredentials {}

  /**
   * @see org.springframework.security.core.Authentication#getAuthorities()
   */
  override def getAuthorities: Collection[GrantedAuthority] = this.authorities

  /**
   * @see org.springframework.security.core.Authentication#getCredentials()
   */
  override def getCredentials = this.role.name()

  /**
   * @see org.springframework.security.core.Authentication#getDetails()
   */
  override def getDetails = null

  /**
   * @see java.security.Principal#getName()
   */
  override def getName = this.name

  /**
   * @see org.springframework.security.core.Authentication#getPrincipal()
   */
  override def getPrincipal = this.principal

  /**
   * @see org.springframework.security.core.Authentication#isAuthenticated()
   */
  override def isAuthenticated = true

  /**
   * @see org.springframework.security.core.Authentication#setAuthenticated(boolean)
   */
  override def setAuthenticated(isAuthenticated: Boolean) {}
}
