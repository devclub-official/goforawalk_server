package side.flab.goforawalk.app.auth

data class UserLoginResponse(
    val userId: Long,
    val credentials: AppAuthToken,
    val userInfo: UserInfo,
)

data class UserInfo(
    val email: String? = null,
    val nickname: String,
)
