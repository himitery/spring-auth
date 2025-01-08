package dev.himitery.spring_auth.auth.application.port.`in`

import dev.himitery.spring_auth.auth.application.port.`in`.dto.SignInCommand
import dev.himitery.spring_auth.auth.application.port.`in`.dto.SignUpCommand
import dev.himitery.spring_auth.auth.application.port.`in`.dto.TokenResponse


interface AuthFacade {
    fun signIn(command: SignInCommand): TokenResponse
    fun signUp(command: SignUpCommand): TokenResponse
}