package dev.himitery.spring_auth.modules.auth.application.service

import dev.himitery.spring_auth.modules.auth.domain.model.Auth

interface AuthUseCase {
    fun save(auth: Auth): Auth
    fun findById(id: String): Auth?
}