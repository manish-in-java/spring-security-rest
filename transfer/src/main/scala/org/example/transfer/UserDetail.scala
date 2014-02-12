package org.example.transfer

import scala.beans.BeanProperty

/**
 * Contains details for a user.
 */
case class UserDetail(
  @BeanProperty val username: String,
  @BeanProperty val role: UserRoleEnum,
  @BeanProperty val firstName: String,
  @BeanProperty val lastName: String) extends TransferObject
