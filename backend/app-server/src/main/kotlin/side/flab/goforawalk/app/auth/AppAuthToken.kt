package side.flab.goforawalk.app.auth

data class AppAuthToken(
    val accessToken: String,
    val refreshToken: String,
)