package side.flab.goforawalk.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import side.flab.goforawalk.security.oauth2.OAuth2OidcAuthenticationProvider
import side.flab.goforawalk.security.oauth2.OAuth2OidcLoginAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        oAuth2OidcLoginAuthenticationFilter: OAuth2OidcLoginAuthenticationFilter,
        oAuth2OidcAuthenticationProvider: OAuth2OidcAuthenticationProvider,
    ): SecurityFilterChain {

        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/docs", "/sample/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(oAuth2OidcLoginAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authenticationProvider(oAuth2OidcAuthenticationProvider)

        return http.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun oAuth2OidcAuthenticationProvider(): OAuth2OidcAuthenticationProvider {
        return OAuth2OidcAuthenticationProvider()
    }

    @Bean
    fun oauth2OidcLoginAuthenticationFilter(
        objectMapper: ObjectMapper,
        authenticationManager: AuthenticationManager,
    ): OAuth2OidcLoginAuthenticationFilter {
        return OAuth2OidcLoginAuthenticationFilter(objectMapper, authenticationManager)
    }
}