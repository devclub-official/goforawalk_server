package side.flab.goforawalk.security.oauth2.validator

import org.springframework.security.oauth2.jwt.Jwt
import side.flab.goforawalk.security.oauth2.OAuth2Provider
import side.flab.goforawalk.security.oauth2.OidcAuthenticationToken

interface OidcIdTokenValidator {
    fun supports(): OAuth2Provider

    fun validate(authentication: OidcAuthenticationToken): Jwt
}