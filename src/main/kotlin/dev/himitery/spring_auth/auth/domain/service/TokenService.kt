package dev.himitery.spring_auth.auth.domain.service

import dev.himitery.spring_auth.auth.application.port.out.TokenCachePort
import dev.himitery.spring_auth.auth.application.service.GenerateTokenUseCase
import dev.himitery.spring_auth.auth.application.service.ValidateTokenUseCase
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class TokenService(
    jwtSecretKey: String,
    private val jwtAccessTokenExpiration: Long,
    private val jwtRefreshTokenExpiration: Long,
    private val tokenCachePort: TokenCachePort
) : ValidateTokenUseCase, GenerateTokenUseCase {

    private val signKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey))
    private val jwtParser = Jwts.parser().verifyWith(signKey).build()

    override fun parseAccessToken(token: String): String? {
        return try {
            val payload = jwtParser.parseSignedClaims(token).payload
            if (payload.expiration.before(Date())) {
                return null
            }

            payload.subject
        } catch (e: JwtException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    override fun parseAccessTokenWithExpired(token: String): String? {
        return try {
            return jwtParser.parseSignedClaims(token)
                .payload
                .subject
        } catch (e: JwtException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    override fun isRefreshTokenValid(token: String, userId: String): Boolean {
        return try {
            val isNotExpired = jwtParser.parseSignedClaims(token)
                .payload
                .expiration
                .after(Date())

            return isNotExpired && tokenCachePort.findByRefreshToken(token) == userId
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    override fun generateAccessToken(datetime: LocalDateTime, userId: String): String {
        return Jwts.builder()
            .subject(userId.toString())
            .expiration(Date(dateToMillis(datetime) + jwtAccessTokenExpiration))
            .signWith(signKey)
            .compact()
    }

    override fun generateRefreshToken(datetime: LocalDateTime, userId: String): String {
        val token = Jwts.builder()
            .expiration(Date(dateToMillis(datetime) + jwtRefreshTokenExpiration))
            .signWith(signKey)
            .compact()

        saveRefreshToken(token, userId)

        return token
    }

    private fun saveRefreshToken(token: String, userId: String) {
        tokenCachePort.saveRefreshToken(token, userId)
    }

    private fun dateToMillis(date: LocalDateTime): Long {
        return date.toInstant(ZoneOffset.UTC).toEpochMilli()
    }
}