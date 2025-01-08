package dev.himitery.spring_auth.modules.auth.application.port.`in`.dto

data class ReissueCommand(
    val accessToken: String,
    val refreshToken: String,
)