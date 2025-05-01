package side.flab.goforawalk.support.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

object JwtUtils {
    private val objectMapper = jacksonObjectMapper()

    fun getPayload(jwt: String): Map<String, Any> {
        val payloadPart = base64Decode(extractPayloadPart(jwt))
        return objectMapper.readValue<Map<String, Any>>(String(payloadPart, Charsets.UTF_8))
    }

    private fun extractPayloadPart(jwt: String): String {
        val split = jwt.split(".")
        if (split.size != 3) {
            throw IllegalArgumentException("Invalid JWT")
        }

        return split[1]
    }

    private fun base64Decode(payloadPart: String): ByteArray =
        java.util.Base64.getUrlDecoder().decode(payloadPart)
}