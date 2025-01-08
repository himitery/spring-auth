package dev.himitery.spring_auth.modules.auth.application.port.`in`.dto

data class SignOutCommand(
    val refreshToken: String,
)
