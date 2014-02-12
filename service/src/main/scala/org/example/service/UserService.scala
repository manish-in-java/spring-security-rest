package org.example.service

import scala.collection.JavaConversions._

import java.util.Collection

import org.example.data.UserRepository
import org.example.domain.User
import org.example.transfer.UserAuthenticationRequest
import org.example.transfer.UserAuthenticationResponse
import org.example.transfer.UserDetail
import org.example.transfer.UserListingRequest
import org.example.transfer.UserListingResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

/**
 * Business logic and data access operations on {@link User}s.
 */
@Service
class UserService {
  @Autowired
  var userRepository: UserRepository = _

  /**
   * Authenticates a user using a username and a password.
   *
   * @param request A {@link UserAuthenticationRequest}.
   * @return A {@link UserAuthenticationResponse}.
   */
  def authenticate(request: UserAuthenticationRequest): UserAuthenticationResponse = {
    val response = new UserAuthenticationResponse

    var user: User = null
    if (request.username == null) {
      response.addError("User authentication failed.  Blank username.")
    } else if (request.password == null) {
      response.addError("User authentication failed.  Blank password.")
    } else if ({ user = this.userRepository.findOne(request.username); user == null }) {
      response.addError(String.format("User authentication failed.  Username [%s] not found.", request.username))
    } else if (user.password != request.password) {
      response.addError(String.format("User authentication failed.  The provided password does not match the password for username [%s].", request.username))
    } else {
      response.firstName = user.firstName
      response.lastName = user.lastName
      response.role = user.role
      response.username = request.username
    }

    return response
  }

  /**
   * Gets all users available in the system.
   *
   * @param request A {@link UserListingRequest}.
   * @return A {@link UserListingResponse}.
   */
  @PreAuthorize("hasRole('ADMIN')")
  def getAll(request: UserListingRequest): UserListingResponse = {
    val response = new UserListingResponse

    for (user <- this.userRepository.findAll) {
      response.addUser(UserDetail(user.username, user.role, user.firstName, user.lastName))
    }

    return response
  }
}
