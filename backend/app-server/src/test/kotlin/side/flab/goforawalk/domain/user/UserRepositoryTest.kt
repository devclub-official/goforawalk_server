package side.flab.goforawalk.domain.user

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import side.flab.goforawalk.domain.user.domain.UserRepository
import side.flab.goforawalk.security.oauth2.OAuth2Provider
import side.flab.goforawalk.domain.user.domain.User
import side.flab.goforawalk.domain.user.domain.UserRole
import side.flab.goforawalk.support.BaseIntegrationTest

class UserRepositoryTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var sut: UserRepository

    @Test
    fun save() {
        val user = User(
            nickname = "test",
            provider = OAuth2Provider.APPLE,
            providerId = "testProviderId",
            role = UserRole.USER
        )

        val actual = sut.save(user)

        assertThat(actual.role).isEqualTo(UserRole.USER)
    }
}