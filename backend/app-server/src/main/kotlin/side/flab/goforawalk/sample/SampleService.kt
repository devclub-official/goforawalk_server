package side.flab.goforawalk.sample

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SampleService(
    private val sampleRepository: SampleRepository
) {
    @Transactional
    fun createSample(request: SampleCreateRequest): SampleEntity {
        val sampleEntity = SampleEntity(
            name = request.name
        )

        return sampleRepository.save(sampleEntity)
    }
}

data class SampleCreateRequest(
    val name: String
)

data class SampleResponse(
    val id: Long,
    val name: String
)