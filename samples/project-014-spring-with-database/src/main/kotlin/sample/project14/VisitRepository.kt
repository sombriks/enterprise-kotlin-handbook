package sample.project14

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

// https://docs.spring.io/spring-data/jpa/reference/repositories/query-keywords-reference.html#appendix.query.method.subject
interface VisitRepository : JpaRepository<Visit, Long?> {

    fun findByCreatedBetween(createdAfter: LocalDateTime, createdBefore: LocalDateTime): List<Visit>

    @Query("select v from Visit v")
    fun listPaged(p: Pageable): Page<Visit>
}
