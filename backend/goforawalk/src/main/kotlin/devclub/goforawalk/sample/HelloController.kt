package devclub.goforawalk.sample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello(): HelloResponse {
        return HelloResponse(
            name = "Hello",
            message = "World!"
        )
    }

    @PostMapping("/hello")
    fun helloPost(): HelloResponse {
        return HelloResponse(
            name = "Hello",
            message = "World!"
        )
    }

    // inner data class로 Request DTO 정의
    data class HelloResponse(
        val name: String,
        val message: String
    )
}
