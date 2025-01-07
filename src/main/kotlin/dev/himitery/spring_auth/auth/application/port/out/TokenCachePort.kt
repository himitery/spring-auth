package dev.himitery.spring_auth.auth.application.port.out

interface TokenCachePort {
    fun saveRefreshToken(refreshToken: String, userId: Long)
    fun findByRefreshToken(refreshToken: String): Long?
}