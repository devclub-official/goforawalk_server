package side.flab.goforawalk.app.docs

import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import side.flab.goforawalk.app.sample.SampleApi

class SampleApiDocTest : DocsTestSupport() {
    @Test
    fun `get-hello`() {
        mockMvc.perform(
            get("/sample/hello")
        )
            .andExpect(status().isOk)
            .andDo(docs)  // 기본 설정된 docs 사용 (method-name 패턴 적용)
    }

    @Test
    fun `post-hello`() {
        val request = SampleApi.HelloRequest("테스터")

        mockMvc.perform(
            post("/sample/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andDo(
                // 추가 스니펫이 필요한 경우 별도로 document() 사용
                docs.document(
                    requestFields(
                        fieldWithPath("name").description("사용자 이름").type(JsonFieldType.STRING)
                    ),
                    responseFields(
                        fieldWithPath("message").description("환영 메시지").type(JsonFieldType.STRING)
                    )
                )
            )
    }
}
