package dev.himitery.spring_auth.auth.application.port.`in`.dto

data class SignUpCommand(
    val id: String,
    val password: String,
    val username: String
)
