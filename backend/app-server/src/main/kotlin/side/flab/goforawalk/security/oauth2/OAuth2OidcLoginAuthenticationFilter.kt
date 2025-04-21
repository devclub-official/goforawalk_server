package side.flab.goforawalk.security.oauth2

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.filter.OncePerRequestFilter

class OAuth2OidcLoginAuthenticationFilter : OncePerRequestFilter() {

    private val requestMatcher: RequestMatcher =
        AntPathRequestMatcher("/api/v1/auth/login/oauth2/**", HttpMethod.POST.name())

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (!requestMatcher.matches(request)) {
            filterChain.doFilter(request, response)
            return
        }


    }
}