package side.flab.goforawalk.app.support

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile

@Profile("test")
@SpringBootTest
abstract class BaseIntegrationTest