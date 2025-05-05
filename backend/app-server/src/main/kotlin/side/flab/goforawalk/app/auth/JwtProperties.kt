package side.flab.goforawalk.app.auth

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.jwt")
class JwtProperties(
    val issuer: String,
    val userNameAttribute: String,
    val atSecretKey: String,
    val rtSecretKey: String,
    val atExpirationSeconds: Long,
    val rtExpirationSeconds: Long,
)