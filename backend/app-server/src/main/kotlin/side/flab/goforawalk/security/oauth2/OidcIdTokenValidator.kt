package side.flab.goforawalk.security.oauth2

import org.springframework.security.oauth2.jwt.Jwt

interface OidcIdTokenValidator {
    fun supports(): OAuth2Provider

    fun validate(authentication: OidcAuthenticationToken): Jwt
}