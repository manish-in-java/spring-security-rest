package org.example.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * User Authentication controller.
 */
@Controller
class UserAuthenticationController {
  /**
   * Displays the login page.
   */
  @RequestMapping(Array("/login"))
  def show = "login"
}
