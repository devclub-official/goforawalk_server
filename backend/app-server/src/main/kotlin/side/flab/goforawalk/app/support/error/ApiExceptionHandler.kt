package side.flab.goforawalk.app.support.error

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import side.flab.goforawalk.app.support.response.ErrorResponse

@RestControllerAdvice
class ApiExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    fun handleIllegalArgumentException(e: RuntimeException): ErrorResponse {
        log.error("Error occurred: ${e.message}", e)
        return ErrorResponse.internalServerError(e.message ?: "Internal server error")
    }
}