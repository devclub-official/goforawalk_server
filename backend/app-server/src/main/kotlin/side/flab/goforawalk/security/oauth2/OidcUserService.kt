package side.flab.goforawalk.security.oauth2

import side.flab.goforawalk.security.UserDetails

interface OidcUserService {
    fun loadUser(userInfo: OidcUserInfo): UserDetails
}