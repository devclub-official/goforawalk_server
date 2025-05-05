package side.flab.goforawalk.app.auth

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import side.flab.goforawalk.app.domain.user.application.AppUserDetails
import side.flab.goforawalk.app.support.response.ApiResponse
import java.nio.charset.StandardCharsets

@Component
class UserLoginSuccessHandler(
    private val tokenGenerator: AppAuthTokenProvider,
    private val objectMapper: ObjectMapper
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val authToken = authentication as UsernamePasswordAuthenticationToken
        val userDetails = authToken.principal as AppUserDetails

        val appToken = tokenGenerator.generate(userDetails)

        writeResponse(response, userDetails, appToken)
    }

    private fun writeResponse(
        response: HttpServletResponse,
        userDetails: AppUserDetails,
        appToken: AppAuthToken
    ) {
        val loginResponse = ApiResponse(
            UserLoginResponse(
                userId = userDetails.getUserId(),
                credentials = appToken,
                userInfo = UserInfo(nickname = userDetails.nickname)
            )
        )

        response.status = HttpServletResponse.SC_OK
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()

        response.writer.use { writer ->
            writer.write(objectMapper.writeValueAsString(loginResponse))
            writer.flush()
        }
    }
}