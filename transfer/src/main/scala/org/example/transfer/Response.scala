package org.example.transfer

import java.util.Collection
import java.util.Collections
import java.util.LinkedHashSet
import java.util.Set

/**
 * Represents a data transfer response.
 */
trait Response extends TransferObject {
  val errors: Set[String] = new LinkedHashSet[String]

  /**
   * Adds an error to the response.
   *
   * @param error The error to add.
   */
  def addError(error: String) {
    if (error != null && error.trim != "") {
      this.errors.add(error)
    }
  }

  /**
   * Gets errors associated with the response.
   *
   * @return A {@link Collection} of errors.
   */
  def getErrors: Collection[String] = Collections.unmodifiableSet(this.errors)

  /**
   * Gets whether this response contains errors.
   *
   * @return Whether this response contains errors.
   */
  def isError = !this.errors.isEmpty

  /**
   * Gets whether this response is a success (does not contain any errors).
   *
   * @return Whether this response is a success.
   */
  def isSuccess = !this.isError
}
