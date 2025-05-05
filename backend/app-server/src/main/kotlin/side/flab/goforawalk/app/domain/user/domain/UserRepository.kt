package side.flab.goforawalk.app.domain.user.domain

import org.springframework.data.jpa.repository.JpaRepository
import side.flab.goforawalk.security.oauth2.OAuth2Provider

interface UserRepository : JpaRepository<User, Long> {
    fun findByProviderAndProviderUsername(
        provider: OAuth2Provider,
        providerUsername: String,
    ) : User?
}