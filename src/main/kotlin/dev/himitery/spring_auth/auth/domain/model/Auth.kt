package dev.himitery.spring_auth.auth.domain.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Auth(
    @Column(nullable = false)
    var username: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(nullable = false, updatable = false)
    lateinit var createdDate: LocalDateTime

    @LastModifiedDate
    @Column(nullable = false)
    lateinit var updatedDate: LocalDateTime
}
