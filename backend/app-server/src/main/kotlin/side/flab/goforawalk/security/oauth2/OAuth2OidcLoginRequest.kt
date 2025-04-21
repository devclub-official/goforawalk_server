package side.flab.goforawalk.security.oauth2

data class OAuth2OidcLoginRequest(
    val idToken: String,
    val userId: String
)