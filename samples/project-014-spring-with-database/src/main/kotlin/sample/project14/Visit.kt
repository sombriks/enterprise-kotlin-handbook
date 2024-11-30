package sample.project14

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Visit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Temporal(TemporalType.TIMESTAMP)
    var created: LocalDateTime? = null,
)
