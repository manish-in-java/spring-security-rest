package org.example.transfer

/**
 * Represents a user authentication request.
 */
case class UserAuthenticationRequest(val username: String, val password: String) extends Request
