package org.example.api

import org.example.service.UserService
import org.example.transfer.UserListingRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * User Administration controller.
 */
@RestController
class UserAdministrationController {
  @Autowired
  var userService: UserService = _

  /**
   * Authenticates an API user using a username and a password.
   */
  @RequestMapping(Array("/users"))
  def show = this.userService.getAll(new UserListingRequest)
}
