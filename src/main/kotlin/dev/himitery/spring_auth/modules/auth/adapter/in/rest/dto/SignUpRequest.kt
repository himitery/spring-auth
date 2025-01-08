package dev.himitery.spring_auth.modules.auth.adapter.`in`.rest.dto

import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.SignUpCommand

data class SignUpRequest(
    val id: String,
    val password: String,
    val username: String,
) {
    fun toCommand() = SignUpCommand(
        id = id,
        password = password,
        username = username,
    )
}
