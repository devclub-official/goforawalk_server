package side.flab.goforawalk.support.utils

import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class JwtUtilsTest {

    @Test
    fun getPayload() {
        val payload = JwtUtils.getPayload(sampleJwt())
        assertThat(payload["iss"]).isEqualTo("https://kauth.kakao.com")
    }

    private fun sampleJwt(): String =
        "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjQ4YTE2MzY5LTdjYTktNGI5ZS05MTQ5LTRmN2U2OWNlZjVkNiJ9.eyJpc3MiOiJodHRwczovL2thdXRoLmtha2FvLmNvbSIsImF1ZCI6ImNiZTU0MzY1ZmVjMzVlNWJlYmYzM2NmNzAyZTFiMDg4IiwiaWF0IjoxNjE1MTgyMTIwLCJleHAiOjE2MTUxODU3MjAsInN1YiI6IjEyMzQ1Njc4OTAiLCJub25jZSI6ImI0ZjA4NWQ5LTg4NTYtNDEzOS04NTA2LWQyZGE1OTVlZmI2NSIsIm5pY2tuYW1lIjoi7ZWc7J6sIiwicGljdHVyZSI6Imh0dHBzOi8vcGYua2FrYW8uY29tL3h4eHh4eHgiLCJlbWFpbCI6InRlc3RAa2FrYW8uY29tIiwiYXV0aF90aW1lIjoxNjE1MTgyMTE1fQ.Jv0RA-XqHCKwfVlOzFXMifE1sxrwAGS0BWzGlh7hOYWrBDZSpyVxv09Rm0zHp5Wh26XCj-XqoXuisAdXBTMZLt2O5zZCXWFG4RgCKMjxbzE9NjxLOFdOh5qjBVq7pcoZL1O8wtHqBsfJ-Mh-QqBV9noDwRArqCz1z1T3j9dOYT87NQ_uGbzsjKioycZe6UMNKGn7pQYZGPS9d_UETkrhlBEQSXzJt-FP3FYbxGdzc_DTGbZ1iYz7UaH2RM7gyY9GoxqvAfqjwh4Af5M1h6HSsNUOQDfoZyXXZMcAQ29A6NVJk0zb8Dwme0Iw-Mpk1jB6UgJ8q8KDZQdKyoHRLvsvVg"
}