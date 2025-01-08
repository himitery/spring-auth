package dev.himitery.spring_auth.auth.application.service

interface ValidateTokenUseCase {
    fun parseAccessToken(token: String): String?
    fun parseAccessTokenWithExpired(token: String): String?
    fun isRefreshTokenValid(token: String, userId: String): Boolean
}