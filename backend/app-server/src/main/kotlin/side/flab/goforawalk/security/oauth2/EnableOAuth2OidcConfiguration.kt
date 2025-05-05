package side.flab.goforawalk.security.oauth2

import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import side.flab.goforawalk.security.oauth2.validator.OidcIdTokenValidatorConfig

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@EnableWebSecurity
@Import(
    OidcSecurityConfig::class,
    OidcIdTokenDecoderConfig::class,
    OidcIdTokenValidatorConfig::class,
)
annotation class EnableOAuth2OidcConfiguration
