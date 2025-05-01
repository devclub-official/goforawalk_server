package side.flab.goforawalk.security.oauth2

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication

private val log = KotlinLogging.logger {}

class OAuth2OidcAuthenticationProvider : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val token = authentication as OAuth2OidcAuthenticationToken

        if (unsupportedProvider(token)) {
            authentication.markAsUnAuthenticated()
            return authentication
        }

        // todo validate kakao id token
        // 1. extract claims from id token, validate.

        // 2. extract signature. validate with kakao public key
        if (validIdToken(token)) {
            token.markAsAuthenticated()
            return token
        }

        authentication.markAsUnAuthenticated()
        return authentication
    }

    private fun unsupportedProvider(token: OAuth2OidcAuthenticationToken) =
        !token.equalsProvider(OAuth2Provider.KAKAO)

    private fun validIdToken(token: OAuth2OidcAuthenticationToken): Boolean {
        TODO("Not yet implemented")
    }

    override fun supports(authentication: Class<*>): Boolean {
        return OAuth2OidcAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}