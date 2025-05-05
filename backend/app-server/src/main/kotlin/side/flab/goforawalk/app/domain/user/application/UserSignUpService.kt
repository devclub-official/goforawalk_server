package side.flab.goforawalk.app.domain.user.application

import org.springframework.stereotype.Service
import side.flab.goforawalk.app.domain.user.domain.User
import side.flab.goforawalk.app.domain.user.domain.UserRepository
import side.flab.goforawalk.security.UserDetails
import side.flab.goforawalk.security.oauth2.OidcUserInfo
import side.flab.goforawalk.security.oauth2.OidcUserService

@Service
class UserSignUpService(
    private val userRepository: UserRepository,
) : OidcUserService {
    override fun loadUser(userInfo: OidcUserInfo): UserDetails {
        val user = userRepository.findByProviderAndProviderUsername(
            userInfo.provider,
            userInfo.providerUsername
        ) ?: createUser(userInfo)

        return AppUserDetails(user.id!!)
    }

    private fun createUser(userInfo: OidcUserInfo): User {
        val user = User(
            provider = userInfo.provider,
            providerUsername = userInfo.providerUsername,
        )
        return userRepository.save(user)
    }
}