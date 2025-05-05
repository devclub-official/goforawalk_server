package side.flab.goforawalk.app.domain.user.application

import org.springframework.stereotype.Service
import side.flab.goforawalk.app.domain.user.domain.UserRepository
import side.flab.goforawalk.security.UserDetails
import side.flab.goforawalk.security.oauth2.OidcUserInfo
import side.flab.goforawalk.security.oauth2.OidcUserService

@Service
class UserSignUpService (
    private val userRepository: UserRepository,
) : OidcUserService {

    override fun loadUser(userInfo: OidcUserInfo): UserDetails {
        TODO("Not yet implemented")
        // if already signed up, return user
        // find by provider username, provider
        userRepository.findByProviderAndProviderUsername(userInfo.provider, userInfo.providerUsername)


        // if exists, return user

        // if not exists, create new user
    }
}