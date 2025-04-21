package side.flab.goforawalk.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import side.flab.goforawalk.security.oauth2.OAuth2OidcLoginAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/docs", "/sample/**").permitAll()
                    .requestMatchers("/api/v1/auth/login/oauth2/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(oauth2OidcLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun oauth2OidcLoginAuthenticationFilter(): OAuth2OidcLoginAuthenticationFilter {
        return OAuth2OidcLoginAuthenticationFilter()
    }
}