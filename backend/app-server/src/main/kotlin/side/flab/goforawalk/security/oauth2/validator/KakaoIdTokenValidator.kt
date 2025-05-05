package side.flab.goforawalk.security.oauth2.validator

import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import side.flab.goforawalk.security.oauth2.OAuth2Provider
import side.flab.goforawalk.security.oauth2.OidcAuthenticationToken

class KakaoIdTokenValidator(
    private val idTokenDecoder: JwtDecoder,
) : OidcIdTokenValidator {
    override fun supports(): OAuth2Provider = OAuth2Provider.KAKAO

    override fun validate(authentication: OidcAuthenticationToken): Jwt =
        idTokenDecoder.decode(authentication.idToken.value)
}