package sample.project14

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

// https://docs.spring.io/spring-data/jpa/reference/repositories/query-keywords-reference.html#appendix.query.method.subject
interface VisitRepository : JpaRepository<Visit, Long?> {

    fun findByCreatedBetween(createdAfter: LocalDateTime, createdBefore: LocalDateTime): List<Visit>
}
