package dev.himitery.spring_auth.modules.auth.application.service

interface InvalidateTokenUseCase {
    fun invalidateRefreshToken(refreshToken: String)
}