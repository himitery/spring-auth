package dev.himitery.spring_auth.auth.application.service

interface ValidateTokenUseCase {
    fun parseAccessToken(token: String): Long?
    fun parseAccessTokenWithExpired(token: String): Long?
    fun isRefreshTokenValid(token: String, userId: String): Boolean
}