package side.flab.goforawalk.security.oauth2

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.beans.factory.BeanInitializationException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenValidator
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import side.flab.goforawalk.security.oauth2.OAuth2Provider.KAKAO
import java.util.concurrent.TimeUnit

@Configuration
class OidcIdTokenDecoderConfig {
    companion object {
        const val JWK_SET_CACHE = "jwkSetCache"
        const val KAKAO_ID_TOKEN_DECODER_BEAN_NAME = "kakaoIdTokenDecoder"
    }

    @Bean
    fun cacheManager(): CacheManager {
        val caches = listOf(
            CaffeineCache(
                JWK_SET_CACHE, Caffeine.newBuilder()
                    .expireAfterWrite(72, TimeUnit.HOURS) // 72시간 후 만료
                    .maximumSize(2) // 제공자가 2개(KAKAO, APPLE) 이므로 2개로 설정
                    .build()
            )
        )

        val cacheManager = SimpleCacheManager()
        cacheManager.setCaches(caches)
        return cacheManager
    }

    @Bean
    fun jwkSetCache(cacheManager: CacheManager): Cache {
        return cacheManager.getCache(JWK_SET_CACHE)
            ?: throw IllegalStateException("$JWK_SET_CACHE cache is not configured.")
    }

    /**
     * @see <a href="https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#oidc-find-public-key">Kakao JWK Set URL</a>
     */
    @Bean(name = [KAKAO_ID_TOKEN_DECODER_BEAN_NAME])
    fun kakaoIdTokenDecoder(
        @Qualifier(JWK_SET_CACHE) jwkSetCache: Cache,
        clientRegistrationRepository: ClientRegistrationRepository,
    ): JwtDecoder {
        val kakaoRegistration = clientRegistrationRepository.findByRegistrationId(KAKAO.provider)
            ?: throw BeanInitializationException("ClientRegistration for provider $KAKAO not found")
        val jwkSetUri = (kakaoRegistration.providerDetails.jwkSetUri
            ?: throw BeanInitializationException("provider $KAKAO jwk-set-uri not found"))

        val kakaoIdTokenDecoder = NimbusJwtDecoder
            .withJwkSetUri(jwkSetUri) // issuer-uri 세팅 시 jwk-set-uri 세팅을 위한 issuer-uri 호출 발생
            .jwsAlgorithm(SignatureAlgorithm.RS256) // 미지정 시 JWKSource 업데이트를 위한 API jwk-set-uri 호출 발생
            .cache(jwkSetCache)
            .build()
        kakaoIdTokenDecoder.setJwtValidator(OidcIdTokenValidator(kakaoRegistration))

        return kakaoIdTokenDecoder
    }
}