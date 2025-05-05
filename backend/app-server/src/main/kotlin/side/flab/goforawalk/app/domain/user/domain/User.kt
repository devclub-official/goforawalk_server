package side.flab.goforawalk.app.domain.user.domain

import jakarta.persistence.*
import side.flab.goforawalk.app.domain.base.BaseEntity
import side.flab.goforawalk.security.oauth2.OAuth2Provider
import java.util.*

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_provider_provider_username",
            columnNames = ["provider", "provider_username"]
        ),
        UniqueConstraint(
            name = "uk_nickname",
            columnNames = ["nickname"]
        )
    ]
)
class User private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "email", length = 50)
    var email: String? = null,

    @Column(name = "nickname", nullable = false, length = 50)
    private var _nickname: String,

    @Column(name = "provider", nullable = false, updatable = false, length = 10)
    @Enumerated(EnumType.STRING)
    val provider: OAuth2Provider,

    @Column(name = "provider_username", nullable = false, updatable = false, length = 30)
    val providerUsername: String,

    @Column(name = "role", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER,
) : BaseEntity() {
    var nickname: String
        get() = _nickname
        set(value) {
            require(value.isNotBlank()) { "nickname must have text" }
            _nickname = value
        }

    constructor(
        provider: OAuth2Provider,
        providerUsername: String,
    ) : this(
        provider = provider,
        providerUsername = providerUsername,
        _nickname = generateRandomNickname(),
    )

    companion object {
        private fun generateRandomNickname(): String {
            val uuid = UUID.randomUUID().toString()
            return "user_${uuid}"
        }
    }

    fun changeNickname(newNickname: String) {
        this.nickname = newNickname
    }
}