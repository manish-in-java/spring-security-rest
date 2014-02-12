package org.example.transfer

/**
 * Represents a user authentication response.
 */
case class UserAuthenticationResponse extends Response {
  /**
   * The user's first name.
   */
  var firstName: String = _

  /**
   * The user's last name.
   */
  var lastName: String = _

  /**
   * The user's full name.
   */
  def name = String.format("%s %s", this.firstName, this.lastName)

  /**
   * The user's application role.
   */
  var role: UserRoleEnum = _

  /**
   * The username for the authenticated user.
   */
  var username: String = _
}
