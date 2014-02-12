package org.example.domain

import org.example.transfer.UserRoleEnum

/**
 * Represents an application user.
 */
case class User(val username: String, val password: String, val firstName: String, val lastName: String, val role: UserRoleEnum) extends Ordered[User] {
  require(firstName != null && firstName.trim != "", "First name must not be null or blank.")
  require(lastName != null && lastName.trim != "", "Last name must not be null or blank.")
  require(username != null && username.trim != "", "Username must not be null or blank.")
  require(password != null && password.trim != "", "Password name must not be null or blank.")
  require(role != null, "Role must not be null.")

  /**
   * {@inheritDoc}
   */
  def compare(that: User): Int = {
    if (this.role != that.role) this.role.compareTo(that.role)
    else if (this.firstName != that.firstName) this.firstName.compare(that.firstName)
    else if (this.lastName != that.lastName) this.lastName.compare(that.lastName)
    else 0
  }
}
