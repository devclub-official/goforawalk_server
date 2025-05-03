package side.flab.goforawalk.security.oauth2

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import side.flab.goforawalk.security.oauth2.validator.OidcIdTokenValidatorFactory

private val log = KotlinLogging.logger {}

/**
 * @see <a href="https://developers.kakao.com/docs/latest/ko/kakaologin/utilize#oidc">Kakao OIDC<a>
 */
class OidcAuthenticationProvider(
    private val idTokenValidatorFactory: OidcIdTokenValidatorFactory
) : AuthenticationProvider {
    override fun supports(authentication: Class<*>) =
        OidcAuthenticationToken::class.java.isAssignableFrom(authentication)

    override fun authenticate(authentication: Authentication): Authentication {
        val token = authentication as OidcAuthenticationToken

        try {
            val idTokenValidator = idTokenValidatorFactory.getValidatorBy(token.provider)
            val idToken = idTokenValidator.validate(token)
            log.info { "Validated id token: $idToken" }
//            TODO("add user service")
            token.markAsAuthenticated()
        } catch (e: RuntimeException) {
            log.warn(e) { "Failed to validate id token: $e" }
            token.markAsUnAuthenticated()
        }

        return authentication
    }
}