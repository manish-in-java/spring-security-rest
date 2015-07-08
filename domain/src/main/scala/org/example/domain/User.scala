package org.example.domain

import org.example.transfer.UserRoleEnum

/**
 * Represents an application user.
 */
case class User(username: String, password: String, firstName: String, lastName: String, role: UserRoleEnum) extends Ordered[User] {
  require(firstName != null, "First name must not be null or blank.")
  require(lastName != null, "Last name must not be null or blank.")
  require(username != null, "Username must not be null or blank.")
  require(password != null, "Password name must not be null or blank.")
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
