package side.flab.goforawalk.security.oauth2.validator

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import side.flab.goforawalk.security.oauth2.OidcIdTokenDecoderConfig

@Configuration
class OidcIdTokenValidatorConfig {
    @Bean
    fun oidcIdTokenValidatorFactory(
        kakaoOidcIdTokenValidator: OidcIdTokenValidator,
    ): OidcIdTokenValidatorFactory {
        return OidcIdTokenValidatorFactory(
            setOf(kakaoOidcIdTokenValidator),
        )
    }

    @Bean
    fun kakaoOidcIdTokenValidator(
        @Qualifier(OidcIdTokenDecoderConfig.KAKAO_ID_TOKEN_DECODER_BEAN_NAME)
        kakaoIdTokenDecoder: JwtDecoder,
    ): OidcIdTokenValidator {
        return KakaoIdTokenValidator(kakaoIdTokenDecoder)
    }
}