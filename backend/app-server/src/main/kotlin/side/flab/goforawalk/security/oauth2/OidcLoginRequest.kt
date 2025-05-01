package side.flab.goforawalk.security.oauth2

data class OidcLoginRequest(
    val idToken: String
) {
    fun toIdToken(): IdToken = IdToken(idToken)
}