package side.flab.goforawalk.sample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sample")
class SampleApi(
    private val sampleService: SampleService
) {
    @GetMapping("/hello")
    fun helloWorld(): String {
        return "Hello World!"
    }

    @PostMapping("/hello")
    fun helloWorld(@RequestBody request: HelloRequest): HelloResponse {
        return HelloResponse("Hello ${request.name}!")
    }

    @PostMapping("/")
    fun createSample(@RequestBody request: SampleCreateRequest): SampleEntity {
        return sampleService.createSample(request)
    }

    data class HelloRequest(
        val name: String
    )

    data class HelloResponse(
        val message: String
    )
}
