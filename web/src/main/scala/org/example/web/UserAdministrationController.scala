package org.example.web

import org.example.service.UserService
import org.example.transfer.UserListingRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
 * User Administration page controller.
 */
@Controller
@PreAuthorize("hasRole('ADMIN')")
class UserAdministrationController {
  @Autowired
  var userService: UserService = _

  /**
   * Displays the User Administration page.
   */
  @RequestMapping(Array("/users"))
  def show(model: Model): String = {
    model.addAttribute("users", this.userService.getAll(UserListingRequest()).getUsers)

    return "user"
  }
}
