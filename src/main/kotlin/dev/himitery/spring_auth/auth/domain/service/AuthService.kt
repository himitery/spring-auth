package dev.himitery.spring_auth.auth.domain.service

import dev.himitery.spring_auth.auth.application.port.out.AuthPersistencePort
import dev.himitery.spring_auth.auth.application.service.AuthUseCase
import dev.himitery.spring_auth.auth.domain.model.Auth
import org.springframework.stereotype.Service

@Service
class AuthService(private val persistencePort: AuthPersistencePort) : AuthUseCase {
    override fun save(auth: Auth): Auth {
        return persistencePort.save(auth)
    }

    override fun findById(id: String): Auth? {
        return persistencePort.findById(id)
    }
}