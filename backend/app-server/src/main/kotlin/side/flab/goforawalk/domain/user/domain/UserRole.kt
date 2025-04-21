package side.flab.goforawalk.domain.user.domain

enum class UserRole {
    USER,
    ADMIN;

    fun isAdmin(): Boolean {
        return this == ADMIN
    }

    fun isUser(): Boolean {
        return this == USER
    }
}