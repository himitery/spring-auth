package dev.himitery.spring_auth.modules.auth.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Auth(
    @Id
    @Column(nullable = false, unique = true)
    var id: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var username: String,
) {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    lateinit var createdDate: LocalDateTime

    @LastModifiedDate
    @Column(nullable = false)
    lateinit var updatedDate: LocalDateTime
}
