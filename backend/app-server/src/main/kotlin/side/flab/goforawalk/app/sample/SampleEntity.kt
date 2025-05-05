package side.flab.goforawalk.app.sample

import jakarta.persistence.*

@Entity
@Table(name = "sample")
class SampleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
)
