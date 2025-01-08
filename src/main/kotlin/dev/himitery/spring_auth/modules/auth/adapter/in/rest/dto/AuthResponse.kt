package dev.himitery.spring_auth.modules.auth.adapter.`in`.rest.dto

import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.TokenResponse

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
) {
    constructor(tokenResponse: TokenResponse) : this(
        tokenResponse.accessToken,
        tokenResponse.refreshToken
    )
}