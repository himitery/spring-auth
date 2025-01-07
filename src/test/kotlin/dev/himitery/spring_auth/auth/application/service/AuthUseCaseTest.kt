package dev.himitery.spring_auth.auth.application.service

import dev.himitery.spring_auth.auth.domain.model.Auth
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthUseCaseTest(private val useCase: AuthUseCase) : FunSpec({
    test("Can save auth") {
        // given
        val auth = Auth("test_user")

        // when
        val saved = useCase.save(auth)

        // then
        saved.shouldNotBeNull()
        saved.shouldBeInstanceOf<Auth>()
    }
})