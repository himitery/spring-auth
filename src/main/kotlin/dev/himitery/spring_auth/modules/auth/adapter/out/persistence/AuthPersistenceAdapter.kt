package dev.himitery.spring_auth.modules.auth.adapter.out.persistence

import dev.himitery.spring_auth.modules.auth.application.port.out.AuthPersistencePort
import dev.himitery.spring_auth.modules.auth.domain.model.Auth
import org.springframework.stereotype.Repository

@Repository
class AuthPersistenceAdapter(private val repository: AuthRepository) : AuthPersistencePort {
    override fun save(auth: Auth): Auth {
        return repository.save(auth)
    }

    override fun findById(id: String): Auth? {
        return repository.findById(id).orElse(null)
    }
}