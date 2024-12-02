package sample.project14

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("visit")
class Controller(
    val em: EntityManager,
    val repo: VisitRepository,
) {

    @GetMapping("em-list-all")
    fun emListAll(): List<Visit> = em.createQuery("select v from Visit v", Visit::class.java).resultList

    @Transactional
    @GetMapping
    fun count(): Long {
        repo.save(Visit())
        return repo.count()
    }

    // some repository usage examples

    @GetMapping("repo-list-all")
    fun repoListAll(): List<Visit> = repo.findAll()

    @GetMapping("repo-list-by-created")
    fun repoListByCreated(start: LocalDateTime, end: LocalDateTime): List<Visit> =
        repo.findByCreatedBetween(start, end)

    @GetMapping("repo-list-paged")
    fun repoListPaged(page: Int, size: Int): Page<Visit> = repo.listPaged(
        PageRequest.of(
            page, size,
            Sort.Direction.DESC, "id"
        )
    )
}
