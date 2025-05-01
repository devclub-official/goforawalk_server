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
import side.flab.goforawalk.security.oauth2.OidcAuthenticationProvider
import side.flab.goforawalk.security.oauth2.OidcIdTokenValidatorFactory
import side.flab.goforawalk.security.oauth2.OidcLoginAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        oidcLoginAuthenticationFilter: OidcLoginAuthenticationFilter,
        oidcAuthenticationProvider: OidcAuthenticationProvider,
    ): SecurityFilterChain {

        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/docs", "/sample/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(oidcLoginAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authenticationProvider(oidcAuthenticationProvider)

        return http.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun oAuth2OidcAuthenticationProvider(
        idTokenValidatorFactory: OidcIdTokenValidatorFactory
    ): OidcAuthenticationProvider {
        return OidcAuthenticationProvider(idTokenValidatorFactory)
    }

    @Bean
    fun oauth2OidcLoginAuthenticationFilter(
        objectMapper: ObjectMapper,
        authenticationManager: AuthenticationManager,
    ): OidcLoginAuthenticationFilter {
        return OidcLoginAuthenticationFilter(objectMapper, authenticationManager)
    }
}