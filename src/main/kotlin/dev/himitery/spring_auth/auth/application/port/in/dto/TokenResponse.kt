package dev.himitery.spring_auth.auth.application.port.`in`.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)