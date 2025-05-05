package side.flab.goforawalk.app.sample

import org.springframework.data.jpa.repository.JpaRepository

interface SampleRepository : JpaRepository<SampleEntity, Long> {

}