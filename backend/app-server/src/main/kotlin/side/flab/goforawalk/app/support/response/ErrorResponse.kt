package side.flab.goforawalk.app.support.response

import java.time.LocalDateTime

data class ErrorResponse(
    val code: String,
    val message: String,
    val timestamp: LocalDateTime,
)