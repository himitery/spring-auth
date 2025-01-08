package dev.himitery.spring_auth.modules.auth.application.port.out

import dev.himitery.spring_auth.modules.auth.domain.model.Auth

interface AuthPersistencePort {
    fun save(auth: Auth): Auth
    fun findById(id: String): Auth?
}