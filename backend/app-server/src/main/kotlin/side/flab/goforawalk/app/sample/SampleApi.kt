package side.flab.goforawalk.app.sample

import org.springframework.web.bind.annotation.*

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
