package side.flab.goforawalk.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.operation.preprocess.Preprocessors

@TestConfiguration
class RestDocsConfiguration {

    @Bean
    fun restDocumentationResultHandler(): RestDocumentationResultHandler {
        return MockMvcRestDocumentation.document(
            "{method-name}", // 스니펫 이름 패턴을 테스트 메서드 이름으로 설정
            Preprocessors.preprocessRequest(
                Preprocessors.prettyPrint(),
                Preprocessors.modifyHeaders()
                    .remove("Content-Length")
                    .set("Host", "35.216.69.99:8080")
            ),
            Preprocessors.preprocessResponse(
                Preprocessors.prettyPrint(),
                Preprocessors.modifyHeaders()
                    .remove("Content-Length")
            )
        )
    }
}
