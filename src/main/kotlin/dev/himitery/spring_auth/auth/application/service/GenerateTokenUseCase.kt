package dev.himitery.spring_auth.auth.application.service

import java.time.LocalDateTime

interface GenerateTokenUseCase {
    fun generateAccessToken(datetime: LocalDateTime, userId: Long): String
    fun generateRefreshToken(datetime: LocalDateTime, userId: Long): String
}