package side.flab.goforawalk.security.oauth2

import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import side.flab.goforawalk.support.BaseRestAssuredTest
import kotlin.test.Test

class KakaoOidcAuthenticationTest : BaseRestAssuredTest() {

    @Test
    fun `KAKAO 제공자로 OIDC 로그인 요청 시 인증 성공해야 한다`() {
        val idToken = IdToken("kakao-id-token")
        val provider = OAuth2Provider.KAKAO

        val response = given()
            .body(idToken)
            .contentType("application/json")
            .`when`()
            .post("/api/v1/auth/login/oauth2/{provider}", provider)

        response
            .then()
            .statusCode(200)
    }
}