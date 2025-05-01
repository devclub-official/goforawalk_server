package side.flab.goforawalk.support

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE

@Profile("test")
@Configuration
class TestConfig(
    private val oauth2ClientProperties: OAuth2ClientProperties,
) {
    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val registrations = mutableListOf<ClientRegistration>()

        val kakaoRegistration = oauth2ClientProperties.registration["kakao"] ?: error("Kakao registration not found")
        val kakaoProvider = oauth2ClientProperties.provider["kakao"] ?: error("Kakao provider not found")

        val clientRegistration = ClientRegistration.withRegistrationId("kakao")
            .clientId(kakaoRegistration.clientId)
            .authorizationGrantType(AuthorizationGrantType(AUTHORIZATION_CODE.value))
            .redirectUri(kakaoRegistration.redirectUri ?: "redirect-uri")
            .authorizationUri(kakaoRegistration.redirectUri ?: "authorization-uri")
            .tokenUri(kakaoProvider.tokenUri ?: "token-uri")
            .issuerUri(kakaoProvider.issuerUri)
            .jwkSetUri(kakaoProvider.jwkSetUri)
            .build()
        registrations.add(clientRegistration)

        return InMemoryClientRegistrationRepository(registrations)
    }
}