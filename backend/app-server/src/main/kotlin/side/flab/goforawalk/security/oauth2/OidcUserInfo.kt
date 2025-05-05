package side.flab.goforawalk.security.oauth2

data class OidcUserInfo(
    val provider: OAuth2Provider,
    val providerUsername: String
)
