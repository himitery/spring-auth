package dev.himitery.spring_auth.modules.auth.application.facade

import dev.himitery.spring_auth.modules.auth.application.port.`in`.AuthFacade
import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.SignInCommand
import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.SignUpCommand
import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.TokenResponse
import dev.himitery.spring_auth.modules.auth.application.service.AuthUseCase
import dev.himitery.spring_auth.modules.auth.application.service.GenerateTokenUseCase
import dev.himitery.spring_auth.modules.auth.domain.exception.LoginIdNotFoundException
import dev.himitery.spring_auth.modules.auth.domain.exception.PasswordMismatchException
import dev.himitery.spring_auth.modules.auth.domain.model.Auth
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
@Transactional(readOnly = true)
class AuthFacadeImpl(
    private val passwordEncoder: PasswordEncoder,
    private val authUseCase: AuthUseCase,
    private val validateTokenUseCase: GenerateTokenUseCase,
    private val generateTokenUseCase: GenerateTokenUseCase
) : AuthFacade {

    @Transactional
    override fun signIn(command: SignInCommand): TokenResponse {
        val auth = authUseCase.findById(command.id) ?: throw LoginIdNotFoundException()
        if (!isPasswordValid(auth, command.password)) {
            throw PasswordMismatchException()
        }

        return generateTokenResponse(auth)
    }

    @Transactional
    override fun signUp(command: SignUpCommand): TokenResponse {
        val auth = authUseCase.save(
            Auth(
                id = command.id,
                password = encryptPassword(command.password),
                username = command.username,
            )
        )

        return generateTokenResponse(auth)
    }

    private fun encryptPassword(password: String): String {
        return passwordEncoder.encode(password)
    }

    private fun isPasswordValid(auth: Auth, password: String): Boolean {
        return passwordEncoder.matches(password, auth.password)
    }

    private fun generateTokenResponse(auth: Auth): TokenResponse {
        val datetime = LocalDateTime.now()
        val accessToken = generateTokenUseCase.generateAccessToken(
            datetime = datetime,
            userId = auth.id
        )
        val refreshToken = generateTokenUseCase.generateRefreshToken(
            datetime = datetime,
            userId = auth.id
        )

        return TokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}