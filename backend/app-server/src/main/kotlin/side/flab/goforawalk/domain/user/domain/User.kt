package side.flab.goforawalk.domain.user.domain

import jakarta.persistence.*
import side.flab.goforawalk.domain.base.BaseEntity
import side.flab.goforawalk.security.oauth2.OAuth2Provider

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "email", length = 50)
    var email: String? = null,

    @Column(name = "nickname", length = 8)
    var nickname: String? = null,

    @Column(name = "provider", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    var provider: OAuth2Provider? = null,

    @Column(name = "provider_id", nullable = false, length = 30)
    var providerId: String? = null,

    @Column(name = "role", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    var role: UserRole? = null
) : BaseEntity()