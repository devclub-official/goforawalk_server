package side.flab.goforawalk.app.domain.user.application

import org.springframework.security.core.GrantedAuthority
import side.flab.goforawalk.security.UserDetails

data class AppUserDetails(
    private val _userId: Long,
    private val _authorities: MutableCollection<GrantedAuthority>,
) : UserDetails {
    override fun getUserId(): Long {
        return _userId
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return _authorities
    }
}
