package dev.himitery.spring_auth.auth.adapter.out.cache

import dev.himitery.spring_auth.auth.application.port.out.TokenCachePort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class TokenCacheAdapter(private val redisTemplate: RedisTemplate<String, Any>) : TokenCachePort {
    override fun saveRefreshToken(refreshToken: String, userId: String) {
        redisTemplate.opsForValue().set(refreshToken, userId)
    }

    override fun findByRefreshToken(refreshToken: String): String? {
        return redisTemplate.opsForValue().get(refreshToken)?.let {
            return it.toString()
        }
    }
}