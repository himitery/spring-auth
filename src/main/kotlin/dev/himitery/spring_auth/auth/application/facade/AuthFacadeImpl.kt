package dev.himitery.spring_auth.auth.application.facade

import dev.himitery.spring_auth.auth.domain.service.AuthService
import org.springframework.stereotype.Component

@Component
class AuthFacadeImpl(private val authService: AuthService) : AuthFacade