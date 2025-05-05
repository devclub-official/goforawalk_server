package side.flab.goforawalk.security.oauth2

import org.springframework.security.authentication.AbstractAuthenticationToken

class OidcAuthenticationToken(
    val idToken: IdToken,
    val provider: OAuth2Provider,
) : AbstractAuthenticationToken(emptyList()) {

    override fun getCredentials(): Any = idToken

    override fun getPrincipal(): Any = provider
}