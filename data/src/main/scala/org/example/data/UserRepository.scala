package org.example.data

import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.List

import org.example.domain.User
import org.example.transfer.UserRoleEnum
import org.springframework.stereotype.Component

/**
 * Contract for data access operations on {@link User}.
 */
trait UserRepository {
  /**
   * Finds all users in the system.
   *
   * @return A {@link Collection} of {@link User}s.
   */
  def findAll: Collection[User]

  /**
   * Finds a user with a specified authentication username.
   *
   * @param username
   *        The username to find.
   * @return A {@link User}.
   */
  def findOne(username: String): User
}

@Component
class UserMemoryRepository extends UserRepository {
  /**
   * {@inheritDoc}
   */
  def findAll: Collection[User] = {
    val users = new ArrayList(UserMemoryRepository.values)

    Collections.sort(users)

    return users
  }

  /**
   * {@inheritDoc}
   */
  def findOne(username: String): User = UserMemoryRepository.get(username)
}

object UserMemoryRepository extends HashMap[String, User] {
  this.put("james", User("james", "password", "James", "Doe", UserRoleEnum.NORMAL))
  this.put("jane", User("jane", "password", "Jane", "Doe", UserRoleEnum.NORMAL))
  this.put("jared", User("jared", "password", "Jared", "Doe", UserRoleEnum.NORMAL))
  this.put("jason", User("jason", "password", "Jason", "Doe", UserRoleEnum.NORMAL))
  this.put("johann", User("johann", "password", "Johann", "Doe", UserRoleEnum.NORMAL))
  this.put("john", User("john", "password", "John", "Doe", UserRoleEnum.ADMIN))
  this.put("joanna", User("joanna", "password", "Joanna", "Doe", UserRoleEnum.NORMAL))
  this.put("jonathan", User("jonathan", "password", "Jonathan", "Doe", UserRoleEnum.ADMIN))
  this.put("joseph", User("joseph", "password", "Joseph", "Doe", UserRoleEnum.ADMIN))
  this.put("judith", User("judith", "password", "Judith", "Doe", UserRoleEnum.NORMAL))
  this.put("julia", User("julia", "password", "Julia", "Doe", UserRoleEnum.ADMIN))
}
