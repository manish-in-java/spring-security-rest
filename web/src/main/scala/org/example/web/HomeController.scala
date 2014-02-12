package org.example.web

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Home page controller.
 */
@Controller
@PreAuthorize("isAuthenticated()")
class HomeController {
  /**
   * Displays the home page.
   */
  @RequestMapping(Array("/"))
  def show(model: Model): String = {
    val authentication = SecurityContextHolder.getContext().getAuthentication()

    model.addAttribute("name", authentication.getName)
    model.addAttribute("role", authentication.getCredentials)

    return "index"
  }
}
