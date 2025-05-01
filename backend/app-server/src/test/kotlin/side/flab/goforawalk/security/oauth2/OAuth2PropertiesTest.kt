package side.flab.goforawalk.security.oauth2

import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import side.flab.goforawalk.support.BaseIntegrationTest
import kotlin.test.Test

class OAuth2PropertiesTest : BaseIntegrationTest() {
    @Autowired
    lateinit var sut: OAuth2Properties

    @Test
    fun oauth2Property_injection() {
        val provider = sut.registration

        assertThat(provider)
            .containsEntry(OAuth2Provider.KAKAO, OAuth2Registration("KAKAO_APP_KEY"))
    }
}