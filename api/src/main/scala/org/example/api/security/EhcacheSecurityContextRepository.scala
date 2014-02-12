package org.example.api.security

import org.springframework.security.core.context.{ SecurityContext, SecurityContextHolder }
import org.springframework.security.web.context.{ HttpRequestResponseHolder, SecurityContextRepository }

import javax.servlet.http.{ HttpServletRequest, HttpServletResponse }

import net.sf.ehcache.{ CacheManager, Element }

/**
 * A {@link SecurityContextRepository} implementation that stores the security context in EHCACHE between requests.
 */
class EhcacheSecurityContextRepository extends SecurityContextRepository {
  /**
   * {@inheritDoc}
   */
  override def containsContext(request: HttpServletRequest) = this.getCache.get(this.getToken(request)) != null

  /**
   * {@inheritDoc}
   */
  override def loadContext(holder: HttpRequestResponseHolder): SecurityContext = {
    if (this.containsContext(holder.getRequest)) this.getCache.get(this.getToken(holder.getRequest)).getObjectValue.asInstanceOf[SecurityContext]
    else SecurityContextHolder.createEmptyContext
  }

  /**
   * {@inheritDoc}
   */
  override def saveContext(context: SecurityContext, request: HttpServletRequest, response: HttpServletResponse) {
    val authentication = context.getAuthentication

    if (authentication != null && authentication.isInstanceOf[APIAuthenticationToken]) {
      val token = authentication.getDetails.asInstanceOf[String]

      if (token != null) {
        this.getCache.put(new Element(token, context))
      }
    }
  }

  /**
   * Gets a cache for storing {@link SecurityContext}s.
   *
   * @return A {@link Cache}.
   */
  private def getCache = EhcacheSecurityContextRepository.CACHE_MANAGER.getCache("SSSC")

  /**
   * Gets the Spring Security authentication token from an {@link HttpServletRequest}.
   *
   * @param request The {@link HttpServletRequest} from which to extract the token.
   * @return The Spring Security authentication token from the request.
   */
  private def getToken(request: HttpServletRequest): String = {
    val token = request.getHeader("X-API-TOKEN")

    if (token != null) token else null
  }
}

private object EhcacheSecurityContextRepository {
  val CACHE_MANAGER = CacheManager.getInstance()
}
