package side.flab.goforawalk.app.docs

import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType.NUMBER
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import side.flab.goforawalk.app.auth.OidcLoginTest.Companion.sampleIdToken
import side.flab.goforawalk.security.oauth2.OAuth2Provider
import side.flab.goforawalk.security.oauth2.OidcLoginRequest
import javax.swing.text.html.HTML.Tag.OBJECT
import kotlin.test.Test

class LoginApiDocsTest : DocsTestSupport(){
    @Test
    fun `login-oidc-success`() {
        val provider = OAuth2Provider.KAKAO
        val request = OidcLoginRequest(sampleIdToken())

        mockMvc.perform(
            post("/api/v1/auth/login/oauth2/{provider}", provider)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andDo(
                docs.document(
                    pathParameters(
                        parameterWithName("provider").description("OAuth2 제공자 (KAKAO/APPLE)")
                    ),
                    requestFields(
                        fieldWithPath("idToken").description("OIDC 제공자로부터 발급받은 ID 토큰").type(STRING),
                    ),
                    responseFields(
                        fieldWithPath("data.userId").description("userId").type(NUMBER),
                        fieldWithPath("data.credentials.accessToken").description("Access Token").type(STRING),
                        fieldWithPath("data.credentials.refreshToken").description("Refresh Token").type(STRING),
                        fieldWithPath("data.userInfo.email").description("email(nullable)").type(STRING).optional(),
                        fieldWithPath("data.userInfo.nickname").description("nickname").type(STRING)
                    )
                )
            )
    }

    @Test
    fun `login-oidc-failure`() {
        val provider = OAuth2Provider.KAKAO
        val request = OidcLoginRequest("invalid_id_token")

        mockMvc.perform(
            post("/api/v1/auth/login/oauth2/{provider}", provider)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isUnauthorized)
            .andDo(
                docs.document(
                    responseFields(
                        fieldWithPath("code").description("API Error Code").type(STRING),
                        fieldWithPath("message").description("Error Message").type(STRING),
                        fieldWithPath("detailMessage").description("Detail Error Message").type(OBJECT).optional(),
                    )
                )
            )
    }
}