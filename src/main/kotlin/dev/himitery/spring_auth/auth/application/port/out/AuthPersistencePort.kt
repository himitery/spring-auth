package dev.himitery.spring_auth.auth.application.port.out

import dev.himitery.spring_auth.auth.domain.model.Auth

interface AuthPersistencePort {
    fun save(auth: Auth): Auth
}