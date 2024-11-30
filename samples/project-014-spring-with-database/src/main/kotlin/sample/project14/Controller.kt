package sample.project14

import jakarta.persistence.EntityManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("visit")
class Controller(val em: EntityManager, val visitRepository: VisitRepository) {

    @GetMapping("count")
    fun visit(): List<Visit> = em.createQuery("select v from Visit v", Visit::class.java).resultList
}
