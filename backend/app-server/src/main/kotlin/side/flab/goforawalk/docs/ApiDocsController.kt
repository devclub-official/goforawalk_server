package side.flab.goforawalk.docs

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * API 문서를 위한 컨트롤러
 * Spring REST Docs로 생성된 문서로 리다이렉트
 */
@Controller
class ApiDocsController {

    /**
     * API 문서 인덱스 페이지로 리다이렉트
     */
    @GetMapping("/docs")
    fun getDocs(): String {
        return "redirect:/docs/index.html"
    }
}
