package side.flab.goforawalk.app.support

import com.github.tomakehurst.wiremock.client.WireMock.*
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.web.servlet.MockMvc
import side.flab.goforawalk.app.auth.OidcLoginTest
import kotlin.test.BeforeTest

@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureWireMock(port = 8089)
abstract class BaseRestAssuredTest : BaseIntegrationTest() {
    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeTest
    fun setup() {
        RestAssuredMockMvc.mockMvc(mockMvc)
    }

    @BeforeTest
    fun setUpOidc() {
        stubFor(
            get(urlEqualTo("/kakao/.well-known/jwks.json"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(OidcLoginTest.kakaoJwkSetJson)
                )
        )
    }
}