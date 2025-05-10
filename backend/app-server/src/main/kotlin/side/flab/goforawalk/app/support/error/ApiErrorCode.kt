package side.flab.goforawalk.app.support.error

enum class ApiErrorCode(
    val defaultMessage: String,
) {
    A_4100("Authentication failed"),
    A_5000("Internal server error"),
}