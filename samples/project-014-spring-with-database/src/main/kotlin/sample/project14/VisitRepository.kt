package sample.project14

import org.springframework.data.jpa.repository.JpaRepository

interface VisitRepository : JpaRepository<Visit, Long?>
