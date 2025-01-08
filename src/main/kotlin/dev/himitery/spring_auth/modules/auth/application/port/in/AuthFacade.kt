package dev.himitery.spring_auth.modules.auth.application.port.`in`

import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.*


interface AuthFacade {
    fun signIn(command: SignInCommand): TokenResponse
    fun signUp(command: SignUpCommand): TokenResponse
    fun reissue(command: ReissueCommand): TokenResponse
    fun signOut(command: SignOutCommand)
}