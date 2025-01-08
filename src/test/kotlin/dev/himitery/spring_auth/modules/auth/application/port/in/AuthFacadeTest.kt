package dev.himitery.spring_auth.modules.auth.application.port.`in`

import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.*
import dev.himitery.spring_auth.modules.auth.domain.exception.InvalidTokenException
import dev.himitery.spring_auth.modules.auth.domain.exception.LoginIdNotFoundException
import dev.himitery.spring_auth.modules.auth.domain.exception.PasswordMismatchException
import dev.himitery.spring_auth.shared.config.TestcontainersConfig
import dev.himitery.spring_auth.shared.utils.mockLocalDateTime
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.time.LocalDateTime

@Import(TestcontainersConfig::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthFacadeTest(facade: AuthFacade) : FunSpec({

    fun signUp(
        command: SignUpCommand = SignUpCommand(
            id = "test_id",
            password = "test_password",
            username = "test_username"
        )
    ): TokenResponse {
        return facade.signUp(command)
    }

    test("Can sign up") {
        // given
        val command = SignUpCommand(
            id = "test_id",
            password = "test_password",
            username = "test_username"
        )

        // when
        val token = signUp(command)

        // then
        token.shouldNotBeNull()
        token.shouldBeInstanceOf<TokenResponse>()
    }

    test("Can sign in") {
        // given
        signUp().let {
            it.shouldNotBeNull()
            it.shouldBeInstanceOf<TokenResponse>()
        }
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
        // given
        signUp().let {
            it.shouldNotBeNull()
            it.shouldBeInstanceOf<TokenResponse>()
        }
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
        // given
        signUp().let {
            it.shouldNotBeNull()
            it.shouldBeInstanceOf<TokenResponse>()
        }
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

    test("Can reissue token") {
        val datetime = LocalDateTime.now()

        var oldToken: TokenResponse? = null
        mockLocalDateTime({
            oldToken = signUp().let {
                it.shouldNotBeNull()
                it.shouldBeInstanceOf<TokenResponse>()
            }
        }, datetime)

        mockLocalDateTime(
            {
                // given
                val command = ReissueCommand(
                    accessToken = oldToken!!.accessToken,
                    refreshToken = oldToken!!.refreshToken
                )

                // when
                Thread.sleep(1000)
                val token = facade.reissue(command)

                // then
                token.shouldNotBeNull()
                token.shouldBeInstanceOf<TokenResponse>()
                token.accessToken.shouldNotBeEqual(oldToken!!.accessToken)
                token.refreshToken.shouldNotBeEqual(oldToken!!.refreshToken)
            },
            datetime.plusMinutes(1),
        )
    }

    test("Can sign out") {
        // given
        val token = signUp()
        val signOutCommand = SignOutCommand(
            refreshToken = token.refreshToken
        )

        // when
        facade.signOut(signOutCommand)

        // then
        shouldThrow<InvalidTokenException> {
            val command = ReissueCommand(
                accessToken = token.accessToken,
                refreshToken = token.refreshToken
            )
            facade.reissue(command)
        }
    }
})