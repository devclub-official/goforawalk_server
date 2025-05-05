package side.flab.goforawalk.app.user

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import side.flab.goforawalk.app.domain.user.domain.User
import side.flab.goforawalk.app.domain.user.domain.UserRepository
import side.flab.goforawalk.app.domain.user.domain.UserRole
import side.flab.goforawalk.app.support.BaseIntegrationTest
import side.flab.goforawalk.security.oauth2.OAuth2Provider

class UserRepositoryTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var sut: UserRepository

    @Test
    fun save() {
        val user = User(
            nickname = "test",
            provider = OAuth2Provider.APPLE,
            providerUsername = "testProviderId",
            role = UserRole.USER
        )

        val actual = sut.save(user)

        assertThat(actual.role).isEqualTo(UserRole.USER)
    }

    @Test
    fun findByProviderAndProviderUsername() {
        // given
        val user = User(
            provider = OAuth2Provider.KAKAO,
            providerUsername = "provider_username",
        )
        sut.save(user)

        val userFound = sut.findByProviderAndProviderUsername(
            provider = OAuth2Provider.KAKAO,
            providerUsername = "provider_username",
        ) !!

        with(userFound) {
            assertThat(this.provider).isEqualTo(OAuth2Provider.KAKAO)
            assertThat(this.providerUsername).isEqualTo("provider_username")
        }
    }
}