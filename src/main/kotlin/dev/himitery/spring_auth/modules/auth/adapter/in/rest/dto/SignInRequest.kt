package dev.himitery.spring_auth.modules.auth.adapter.`in`.rest.dto

import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.SignInCommand

data class SignInRequest(
    val id: String,
    val password: String,
) {
    fun toCommand() = SignInCommand(
        id = id,
        password = password,
    )
}