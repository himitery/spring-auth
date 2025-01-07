package dev.himitery.spring_auth.auth.application.service

import dev.himitery.spring_auth.auth.domain.model.Auth

interface AuthUseCase {
    fun save(auth: Auth): Auth
}