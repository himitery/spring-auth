package dev.himitery.spring_auth.modules.auth.application.service

import dev.himitery.spring_auth.modules.auth.application.port.out.TokenCachePort
import dev.himitery.spring_auth.shared.config.TestcontainersConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.time.LocalDateTime


@Import(TestcontainersConfig::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InvalidateTokenUseCaseTest(
    useCase: InvalidateTokenUseCase,
    generateTokenUseCase: GenerateTokenUseCase,
    tokenCachePort: TokenCachePort,
) : FunSpec({

    test("Can invalidate Refresh Token") {
        // given
        val refreshToken = generateTokenUseCase.generateRefreshToken(
            datetime = LocalDateTime.now(),
            userId = "test_id"
        ).shouldNotBeNull()

        // when
        useCase.invalidateRefreshToken(refreshToken)

        // then
        tokenCachePort.findByRefreshToken(refreshToken).shouldBeNull()
    }
})