package side.flab.goforawalk.app.support

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import side.flab.goforawalk.app.domain.user.domain.UserRepository
import kotlin.test.BeforeTest

@Profile("test")
@SpringBootTest
abstract class BaseIntegrationTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @BeforeTest
    fun clearData() {
        userRepository.deleteAllInBatch()
    }
}