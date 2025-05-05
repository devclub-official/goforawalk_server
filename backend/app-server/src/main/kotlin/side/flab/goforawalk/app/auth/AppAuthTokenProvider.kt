package side.flab.goforawalk.app.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import side.flab.goforawalk.app.domain.user.application.AppUserDetails
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

@Component
class AppAuthTokenProvider(
    private val properties: JwtProperties
) {
    fun generate(userDetails: AppUserDetails): AppAuthToken {
        val now = Instant.now()

        val accessToken = generateJwt(
            userDetails,
            properties.atSecretKey,
            properties.atExpirationSeconds,
            now
        )
        val refreshToken = generateJwt(
            userDetails,
            properties.rtSecretKey,
            properties.rtExpirationSeconds,
            now
        )

        return AppAuthToken(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    private fun generateJwt(
        userDetails: AppUserDetails,
        secretKey: String,
        expirationSeconds: Long,
        now: Instant
    ): String {
        return Jwts.builder()
            .subject(userDetails.getUserId().toString())
            .issuer(properties.issuer)
            .claim("nickname", userDetails.nickname)
            .issuedAt(Date.from(now))
            .expiration(toExpirationSeconds(now, expirationSeconds))
            .signWith(toSigningKey(secretKey))
            .compact()
    }

    private fun toSigningKey(secretKey: String): SecretKey =
        Keys.hmacShaKeyFor(secretKey.toByteArray())

    private fun toExpirationSeconds(now: Instant, expirationSeconds: Long): Date =
        Date.from(now.plusSeconds(expirationSeconds))
}