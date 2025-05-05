package side.flab.goforawalk.app.docs

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.context.annotation.Import
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import side.flab.goforawalk.app.support.BaseRestAssuredTest

@Tag("restdocs")
@AutoConfigureRestDocs
@Import(RestDocsConfiguration::class)
abstract class DocsTestSupport : BaseRestAssuredTest() {
    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var docs: RestDocumentationResultHandler
}