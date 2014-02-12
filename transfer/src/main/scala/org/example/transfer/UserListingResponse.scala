package org.example.transfer

import java.util.ArrayList
import java.util.Collection
import java.util.Collections

/**
 * Represents a user listing response.
 */
case class UserListingResponse extends Response {
  var users: Collection[UserDetail] = _

  /**
   * Adds a user to the response.
   *
   * @param user
   *        A {@link UserDetail} to add to the response.
   */
  def addUser(user: UserDetail) {
    if (user != null) {
      if (this.users == null) {
        this.users = new ArrayList[UserDetail]
      }

      this.users.add(user)
    }
  }

  /**
   * Gets users for the system.
   *
   * @return A {@link Collection} of {@link UserDetail}s.
   */
  def getUsers(): Collection[UserDetail] = if (this.users != null) Collections.unmodifiableCollection(this.users) else Collections.emptyList[UserDetail]
}
