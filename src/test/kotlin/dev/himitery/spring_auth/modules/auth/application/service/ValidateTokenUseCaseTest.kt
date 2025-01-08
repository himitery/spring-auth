package dev.himitery.spring_auth.modules.auth.application.service

import dev.himitery.spring_auth.shared.config.TestcontainersConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.time.LocalDateTime

@Import(TestcontainersConfig::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidateTokenUseCaseTest(
    useCase: ValidateTokenUseCase,
    generateTokenUseCase: GenerateTokenUseCase
) : FunSpec({

    test("Can parse Access Token when not expired") {
        // given
        val userId = "test_id"
        val datetime = LocalDateTime.now()
        val accessToken = generateTokenUseCase.generateAccessToken(datetime, userId)

        // when
        val parsed = useCase.parseAccessToken(accessToken)

        // then
        parsed.shouldNotBeNull()
        parsed.shouldBeEqual(userId)
    }

    test("Can't parse Access Token when expired without option") {
        // given
        val userId = "test_id"
        val datetime = LocalDateTime.now().minusYears(10)
        val accessToken = generateTokenUseCase.generateAccessToken(datetime, userId)

        // when
        val parsed = useCase.parseAccessToken(accessToken)

        // then
        parsed.shouldBeNull()
    }

    test("Can parse Access Token with expired option") {
        // given
        val userId = "test_id"
        val datetime = LocalDateTime.now()
        val accessToken = generateTokenUseCase.generateAccessToken(datetime, userId)

        // when
        val parsed = useCase.parseAccessTokenWithExpired(accessToken)

        // then
        parsed.shouldNotBeNull()
        parsed.shouldBeEqual(userId)
    }

    test("Can valid Refresh Token when not expired") {
        // given
        val userId = "test_id"
        val datetime = LocalDateTime.now()
        val refreshToken = generateTokenUseCase.generateRefreshToken(datetime, userId)

        // when
        val parsed = useCase.isRefreshTokenValid(refreshToken, userId)

        // then
        parsed.shouldBeEqual(true)
    }

    test("Can't valid Refresh Token when expired") {
        // given
        val userId = "test_id"
        val datetime = LocalDateTime.now().minusYears(10)
        val refreshToken = generateTokenUseCase.generateRefreshToken(datetime, userId)

        // when
        val parsed = useCase.isRefreshTokenValid(refreshToken, userId)

        // then
        parsed.shouldBeEqual(false)
    }

    test("Can't valid Refresh Token when userId is different") {
        // given
        val userId = "test_id"
        val datetime = LocalDateTime.now()
        val refreshToken = generateTokenUseCase.generateRefreshToken(datetime, userId)

        // when
        val parsed = useCase.isRefreshTokenValid(refreshToken, userId + 1)

        // then
        parsed.shouldBeEqual(false)
    }
})