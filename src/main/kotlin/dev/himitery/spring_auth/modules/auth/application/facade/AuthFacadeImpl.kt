package dev.himitery.spring_auth.modules.auth.application.facade

import dev.himitery.spring_auth.modules.auth.application.port.`in`.AuthFacade
import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.*
import dev.himitery.spring_auth.modules.auth.application.service.AuthUseCase
import dev.himitery.spring_auth.modules.auth.application.service.GenerateTokenUseCase
import dev.himitery.spring_auth.modules.auth.application.service.InvalidateTokenUseCase
import dev.himitery.spring_auth.modules.auth.application.service.ValidateTokenUseCase
import dev.himitery.spring_auth.modules.auth.domain.exception.InvalidTokenException
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
    private val validateTokenUseCase: ValidateTokenUseCase,
    private val generateTokenUseCase: GenerateTokenUseCase,
    private val invalidateTokenUseCase: InvalidateTokenUseCase
) : AuthFacade {

    override fun signIn(command: SignInCommand): TokenResponse {
        val auth = authUseCase.findById(command.id) ?: throw LoginIdNotFoundException()
        if (!isPasswordValid(auth, command.password)) {
            throw PasswordMismatchException()
        }

        return generateTokenResponse(auth.id)
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

        return generateTokenResponse(auth.id)
    }

    override fun reissue(command: ReissueCommand): TokenResponse {
        val authId = validateTokenUseCase.parseAccessTokenWithExpired(command.accessToken)
            ?: throw InvalidTokenException()
        if (!validateTokenUseCase.isRefreshTokenValid(command.refreshToken, authId)) {
            throw InvalidTokenException()
        }

        return generateTokenResponse(authId)
    }

    override fun signOut(command: SignOutCommand) {
        invalidateTokenUseCase.invalidateRefreshToken(command.refreshToken)
    }

    private fun encryptPassword(password: String): String {
        return passwordEncoder.encode(password)
    }

    private fun isPasswordValid(auth: Auth, password: String): Boolean {
        return passwordEncoder.matches(password, auth.password)
    }

    private fun generateTokenResponse(authId: String): TokenResponse {
        val datetime = LocalDateTime.now()
        val accessToken = generateTokenUseCase.generateAccessToken(
            datetime = datetime,
            userId = authId
        )
        val refreshToken = generateTokenUseCase.generateRefreshToken(
            datetime = datetime,
            userId = authId
        )

        return TokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}