package side.flab.goforawalk.security.oauth2

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.stereotype.Component
import side.flab.goforawalk.security.oauth2.OidcIdTokenDecoderConfig.Companion.KAKAO_ID_TOKEN_DECODER_BEAN_NAME

@Component
class KakaoIdTokenValidator(
    @Qualifier(KAKAO_ID_TOKEN_DECODER_BEAN_NAME)
    private val idTokenDecoder: JwtDecoder,
) : OidcIdTokenValidator {
    override fun supports(): OAuth2Provider = OAuth2Provider.KAKAO

    override fun validate(authentication: OidcAuthenticationToken): Jwt =
        idTokenDecoder.decode(authentication.idToken.value)
}