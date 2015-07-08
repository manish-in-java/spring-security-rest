package org.example.security

import java.security.SecureRandom

/**
 * Generates string tokens.
 */
object TokenGenerator {
  val LENGTH = 32

  private[this] val ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
  private[this] val RANDOM = new SecureRandom

  /**
   * Generates a string token.
   */
  def generateToken: String = {
    val builder = new StringBuilder

    for (i <- 1 to LENGTH) {
      builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length)))
    }

    builder.toString()
  }
}
