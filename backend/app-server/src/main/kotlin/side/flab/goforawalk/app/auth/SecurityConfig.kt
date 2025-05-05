package side.flab.goforawalk.app.auth

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import side.flab.goforawalk.security.oauth2.EnableOAuth2OidcConfiguration
import side.flab.goforawalk.security.oauth2.OidcAuthenticationProvider
import side.flab.goforawalk.security.oauth2.OidcLoginAuthenticationFilter

@Configuration
@EnableOAuth2OidcConfiguration
@EnableConfigurationProperties(JwtProperties::class)
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
            // todo add successHandler, failureHandler
            .addFilterBefore(oidcLoginAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authenticationProvider(oidcAuthenticationProvider) // todo oidcAuthenticationProvider를 직접 등록 하지 않도록 수정

        return http.build()
    }

    @Bean
    fun oauth2OidcLoginAuthenticationFilter(
        objectMapper: ObjectMapper,
        authenticationManager: AuthenticationManager,
        userLoginSuccessHandler: UserLoginSuccessHandler
    ): OidcLoginAuthenticationFilter {
        return OidcLoginAuthenticationFilter(
            objectMapper,
            authenticationManager,
            userLoginSuccessHandler,
            UserLoginFailureHandler(),
        )
    }
}