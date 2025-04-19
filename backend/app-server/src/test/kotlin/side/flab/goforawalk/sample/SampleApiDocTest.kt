package side.flab.goforawalk.sample

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import side.flab.goforawalk.config.RestDocsConfiguration

@WebMvcTest(SampleApi::class)
@AutoConfigureRestDocs
@Import(RestDocsConfiguration::class)
class SampleApiDocTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var docs: RestDocumentationResultHandler

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
