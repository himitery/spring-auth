package dev.himitery.spring_auth.modules.auth.application.port.`in`.dto

data class SignInCommand(
    val id: String,
    val password: String,
)
