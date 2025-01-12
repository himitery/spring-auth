package dev.himitery.spring_auth.modules.auth.application.service

import dev.himitery.spring_auth.modules.auth.domain.model.Auth
import dev.himitery.spring_auth.shared.config.TestcontainersConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfig::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthUseCaseTest(private val useCase: AuthUseCase) :
    FunSpec({
        test("Can save auth") {
            // given
            val auth = Auth("test_id", "test_password", "test_username")

            // when
            val saved = useCase.save(auth)

            // then
            saved.shouldNotBeNull()
            saved.shouldBeInstanceOf<Auth>()
        }
    })