package side.flab.goforawalk.security.oauth2

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.filter.OncePerRequestFilter

private val log = KotlinLogging.logger {}

class OidcLoginAuthenticationFilter(
    private val objectMapper: ObjectMapper,
    private val authenticationManager: AuthenticationManager,
    private val successHandler: AuthenticationSuccessHandler,
    private val failureHandler: AuthenticationFailureHandler,
) : OncePerRequestFilter() {

    companion object {
        const val OIDC_LOGIN_PATH_PATTERN = "/api/v1/auth/login/oauth2/**"
    }

    private val requestMatcher: RequestMatcher = AntPathRequestMatcher(OIDC_LOGIN_PATH_PATTERN, HttpMethod.POST.name())

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if (!requestMatcher.matches(request)) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val authResult = attemptAuthentication(request)
            if (authResult.isAuthenticated) {
                successHandler.onAuthenticationSuccess(request, response, authResult)
            }
        } catch (e: AuthenticationException) {
            SecurityContextHolder.clearContext()
            failureHandler.onAuthenticationFailure(request, response, e)
        }
    }

    private fun attemptAuthentication(request: HttpServletRequest): Authentication {
        val authentication = generateAuthRequest(request)
        return authenticationManager.authenticate(authentication)
    }

    private fun generateAuthRequest(request: HttpServletRequest): OidcAuthenticationToken {
        val provider = OAuth2Provider.valueOf(extractProvider(request))
        val loginRequest = objectMapper.readValue(request.inputStream, OidcLoginRequest::class.java)
        log.info { "OIDC Login request: provider=$provider & idToken=${loginRequest.idToken}" }

        return OidcAuthenticationToken(loginRequest.toIdToken(), provider)
    }

    private fun extractProvider(request: HttpServletRequest): String {
        val path = request.requestURI
        return path.substringAfterLast("/")
    }
}