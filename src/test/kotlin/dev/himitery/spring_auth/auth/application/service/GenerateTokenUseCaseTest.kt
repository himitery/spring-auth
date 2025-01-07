package dev.himitery.spring_auth.auth.application.service

import dev.himitery.spring_auth.auth.application.port.out.TokenCachePort
import dev.himitery.spring_auth.config.TestcontainersConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.time.LocalDateTime

@Import(TestcontainersConfig::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GenerateTokenUseCaseTest(
    useCase: GenerateTokenUseCase,
    tokenCachePort: TokenCachePort,
) :
    FunSpec({
        test("Can generate Access Token") {
            // given
            val userId = 0L
            val datetime = LocalDateTime.now()

            // when
            val accessToken = useCase.generateAccessToken(datetime, userId)

            // then
            accessToken.shouldNotBeNull()
            accessToken.shouldBeInstanceOf<String>()
        }

        test("Can generate Refresh Token") {
            // given
            val userId = 0L
            val datetime = LocalDateTime.now()

            // when
            val refreshToken = useCase.generateRefreshToken(datetime, userId)

            // then
            refreshToken.shouldNotBeNull()
            refreshToken.shouldBeInstanceOf<String>()
        }

        test("Refresh Token saved in cache when generated") {
            // given
            val userId = 0L
            val datetime = LocalDateTime.now()
            val refreshToken = useCase.generateRefreshToken(datetime, userId)

            // when
            val cached = tokenCachePort.findByRefreshToken(refreshToken)

            // then
            cached.shouldNotBeNull()
            cached.shouldBeEqual(userId)
        }
    })