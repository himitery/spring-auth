package dev.himitery.spring_auth.auth.adapter.out.persistence

import dev.himitery.spring_auth.auth.application.port.out.AuthPersistencePort
import dev.himitery.spring_auth.auth.domain.model.Auth
import org.springframework.stereotype.Repository

@Repository
class AuthPersistenceAdapter(private val repository: AuthRepository) : AuthPersistencePort {
    override fun save(auth: Auth): Auth {
        return repository.save(auth)
    }
}