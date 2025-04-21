package side.flab.goforawalk.docs

import org.junit.jupiter.api.Tag
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import

@Tag("restdocs")
@WebMvcTest(
    excludeAutoConfiguration = [SecurityAutoConfiguration::class]
)
@AutoConfigureRestDocs
@Import(RestDocsConfiguration::class)
abstract class DocsTestSupport {
}