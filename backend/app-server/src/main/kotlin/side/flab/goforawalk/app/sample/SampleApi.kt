package side.flab.goforawalk.app.sample

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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

    @PostMapping("/upload-image")
    fun uploadSampleImage(
        @RequestParam file: MultipartFile,
    ): HelloResponse {
        return HelloResponse("file '${file.originalFilename}' uploaded successfully!")
    }

    data class HelloRequest(
        val name: String
    )

    data class HelloResponse(
        val message: String
    )
}
