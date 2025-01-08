package dev.himitery.spring_auth.modules.auth.adapter.`in`.rest

import dev.himitery.spring_auth.modules.auth.adapter.`in`.rest.dto.AuthResponse
import dev.himitery.spring_auth.modules.auth.adapter.`in`.rest.dto.ReissueRequest
import dev.himitery.spring_auth.modules.auth.adapter.`in`.rest.dto.SignInRequest
import dev.himitery.spring_auth.modules.auth.adapter.`in`.rest.dto.SignUpRequest
import dev.himitery.spring_auth.modules.auth.application.port.`in`.AuthFacade
import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.ReissueCommand
import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.SignOutCommand
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Auth")
@RequestMapping("/v1/auth")
class AuthController(
    private val authFacade: AuthFacade
) {

    @PostMapping("/login")
    fun signIn(@RequestBody req: SignInRequest) = authFacade.signIn(req.toCommand()).let {
        ResponseEntity.ok(AuthResponse(it))
    }

    @PostMapping("/new")
    fun signUp(@RequestBody req: SignUpRequest) = authFacade.signUp(req.toCommand()).let {
        ResponseEntity.ok(AuthResponse(it))
    }

    @PatchMapping("/reissue")
    fun reIssue(
        @RequestBody req: ReissueRequest,
        @RequestHeader("Refresh-Token") refreshToken: String
    ) = authFacade.reissue(ReissueCommand(req.accessToken, refreshToken)).let {
        ResponseEntity.ok(AuthResponse(it))
    }

    @DeleteMapping("/logout")
    fun signOut(@RequestHeader("Refresh-Token") refreshToken: String) =
        authFacade.signOut(SignOutCommand(refreshToken)).let {
            ResponseEntity.ok().build<Void>()
        }
}