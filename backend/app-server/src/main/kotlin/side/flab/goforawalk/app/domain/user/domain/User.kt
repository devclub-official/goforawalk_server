package side.flab.goforawalk.app.domain.user.domain

import jakarta.persistence.*
import side.flab.goforawalk.app.domain.base.BaseEntity
import side.flab.goforawalk.security.oauth2.OAuth2Provider

@Entity
@Table(name = "users")
// todo unique 제약조건 추가
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "email", length = 50)
    var email: String? = null,

    @Column(name = "nickname", length = 8)
    var nickname: String? = null,

    @Column(name = "provider", nullable = false, updatable = false, length = 10)
    @Enumerated(EnumType.STRING)
    val provider: OAuth2Provider,

    @Column(name = "provider_username", nullable = false, updatable = false, length = 30)
    val providerUsername: String,

    @Column(name = "role", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER
) : BaseEntity()