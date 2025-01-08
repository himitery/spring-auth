package dev.himitery.spring_auth.modules.auth.config

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.expiration.access}") private val accessTokenExpiration: String,
    @Value("\${jwt.expiration.refresh}") private val refreshRefreshExpiration: String
) {
    @PostConstruct
    fun valid() {
        require(secretKey.isNotBlank()) { "JWT secret key must not be blank" }
        require(accessTokenExpiration.isNotBlank()) { "Access token expiration must not be blank" }
        require(refreshRefreshExpiration.isNotBlank()) { "Refresh token expiration must not be blank" }
    }

    @Bean
    fun jwtSecretKey(): String {
        return secretKey
    }

    @Bean
    fun jwtAccessTokenExpiration(): Long {
        return accessTokenExpiration.toLong()
    }

    @Bean
    fun jwtRefreshTokenExpiration(): Long {
        return refreshRefreshExpiration.toLong()
    }
}