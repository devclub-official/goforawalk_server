package side.flab.goforawalk.security.oauth2

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import side.flab.goforawalk.security.oauth2.validator.OidcIdTokenValidatorFactory

@Configuration
class OidcSecurityConfig {
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun oAuth2OidcAuthenticationProvider(
        idTokenValidatorFactory: OidcIdTokenValidatorFactory,
        userService: OidcUserService
    ): OidcAuthenticationProvider {
        return OidcAuthenticationProvider(idTokenValidatorFactory, userService)
    }

    @Bean
    fun oauth2OidcLoginAuthenticationFilter(
        objectMapper: ObjectMapper,
        authenticationManager: AuthenticationManager,
    ): OidcLoginAuthenticationFilter {
        return OidcLoginAuthenticationFilter(objectMapper, authenticationManager)
    }
}