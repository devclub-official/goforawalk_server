package side.flab.goforawalk.app.domain.user.application

import side.flab.goforawalk.security.UserDetails

data class AppUserDetails(
    private val _userId: Long,
    val nickname: String,
) : UserDetails {
    override fun getUserId(): Long {
        return _userId
    }
}
