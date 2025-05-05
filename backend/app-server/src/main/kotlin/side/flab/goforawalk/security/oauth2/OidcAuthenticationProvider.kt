package side.flab.goforawalk.security.oauth2

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import side.flab.goforawalk.security.oauth2.validator.OidcIdTokenValidatorFactory

private val log = KotlinLogging.logger {}

class OidcAuthenticationProvider(
    private val idTokenValidatorFactory: OidcIdTokenValidatorFactory,
    private val userService: OidcUserService,
) : AuthenticationProvider {

    override fun supports(authentication: Class<*>) =
        OidcAuthenticationToken::class.java.isAssignableFrom(authentication)

    override fun authenticate(authentication: Authentication): Authentication {
        val token = authentication as OidcAuthenticationToken

        try {
            val validatedIdToken = validateIdToken(token)
            val userDetails =
                userService.loadUser(OidcUserInfo(token.provider, validatedIdToken.claims["sub"] as String))

            return UsernamePasswordAuthenticationToken.authenticated(
                userDetails,
                null,
                emptyList()
            )
        } catch (e: RuntimeException) {
            throw BadCredentialsException("Failed to validate id token: ${e.message}", e)
        }
    }

    private fun validateIdToken(token: OidcAuthenticationToken): Jwt {
        val idTokenValidator = idTokenValidatorFactory.getValidatorBy(token.provider)
        val validatedIdToken = idTokenValidator.validate(token)
        log.info { "${token.provider} id token validated: ${validatedIdToken.headers} ${validatedIdToken.claims}" }
        return validatedIdToken
    }
}