package dev.himitery.spring_auth.auth.application.port.`in`

import dev.himitery.spring_auth.auth.application.port.`in`.dto.SignInCommand
import dev.himitery.spring_auth.auth.application.port.`in`.dto.SignUpCommand
import dev.himitery.spring_auth.auth.application.port.`in`.dto.TokenResponse
import dev.himitery.spring_auth.auth.domain.exception.LoginIdNotFoundException
import dev.himitery.spring_auth.auth.domain.exception.PasswordMismatchException
import dev.himitery.spring_auth.config.TestcontainersConfig
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfig::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthFacadeTest(facade: AuthFacade) : FunSpec({

    test("Can sign up") {
        // given
        val command = SignUpCommand(
            id = "test_id",
            password = "test_password",
            username = "test_username"
        )

        // when
        val token = facade.signUp(command)

        // then
        token.shouldNotBeNull()
        token.shouldBeInstanceOf<TokenResponse>()
    }

    test("Can sign in") {
        facade.signUp(
            SignUpCommand(
                id = "test_id",
                password = "test_password",
                username = "test_username"
            )
        )

        // given
        val command = SignInCommand(
            id = "test_id",
            password = "test_password"
        )

        // when
        val token = facade.signIn(command)

        // then
        token.shouldNotBeNull()
        token.shouldBeInstanceOf<TokenResponse>()
    }

    test("Can't sign in when given invalid id") {
        facade.signUp(
            SignUpCommand(
                id = "test_id",
                password = "test_password",
                username = "test_username"
            )
        )

        // given
        val command = SignInCommand(
            id = "test_invalid_id",
            password = "test_password"
        )

        // when
        val throwable = { facade.signIn(command) }

        // then
        shouldThrow<LoginIdNotFoundException> {
            throwable()
        }
    }

    test("Can't sign in when given invalid password") {
        facade.signUp(
            SignUpCommand(
                id = "test_id",
                password = "test_password",
                username = "test_username"
            )
        )

        // given
        val command = SignInCommand(
            id = "test_id",
            password = "test_invalid_password"
        )

        // when
        val throwable = { facade.signIn(command) }

        // then
        shouldThrow<PasswordMismatchException> {
            throwable()
        }
    }
})