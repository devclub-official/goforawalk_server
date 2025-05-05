package side.flab.goforawalk.security

import org.springframework.security.core.GrantedAuthority

interface UserDetails {
    fun getUserId(): Long
    fun getAuthorities(): Collection<GrantedAuthority?>?
}