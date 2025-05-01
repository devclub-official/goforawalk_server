package side.flab.goforawalk.security.oauth2

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.security.oauth2")
class OAuth2Properties (
    // app.security.oauth2.registration에 매핑됨
    val registration: Map<OAuth2Provider, OAuth2Registration>
)

data class OAuth2Registration(
    val appKey: String
)