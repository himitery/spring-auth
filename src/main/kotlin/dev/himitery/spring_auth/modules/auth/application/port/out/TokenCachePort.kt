package dev.himitery.spring_auth.modules.auth.application.port.out

interface TokenCachePort {
    fun saveRefreshToken(refreshToken: String, userId: String)
    fun findByRefreshToken(refreshToken: String): String?
    fun deleteRefreshToken(refreshToken: String)
}