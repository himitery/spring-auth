package dev.himitery.spring_auth.modules.auth.adapter.`in`.rest

import dev.himitery.spring_auth.modules.auth.adapter.`in`.rest.dto.AuthResponse
import dev.himitery.spring_auth.modules.auth.adapter.`in`.rest.dto.ReissueRequest
import dev.himitery.spring_auth.modules.auth.adapter.`in`.rest.dto.SignInRequest
import dev.himitery.spring_auth.modules.auth.adapter.`in`.rest.dto.SignUpRequest
import dev.himitery.spring_auth.modules.auth.application.port.`in`.AuthFacade
import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.ReissueCommand
import dev.himitery.spring_auth.modules.auth.application.port.`in`.dto.SignOutCommand
import dev.himitery.spring_auth.shared.filter.SecurityFilter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "인증")
@RequestMapping("/v1/auth")
class AuthController(
    private val authFacade: AuthFacade
) {

    @PostMapping("/login")
    @Operation(
        summary = "로그인",
    )
    fun signIn(@RequestBody req: SignInRequest) = authFacade.signIn(req.toCommand()).let {
        ResponseEntity.ok(AuthResponse(it))
    }

    @PostMapping("/new")
    @Operation(
        summary = "회원가입",
    )
    fun signUp(@RequestBody req: SignUpRequest) = authFacade.signUp(req.toCommand()).let {
        ResponseEntity.ok(AuthResponse(it))
    }

    @PatchMapping("/reissue")
    @Operation(
        summary = "토큰 재발급",
    )
    fun reissue(
        @RequestBody req: ReissueRequest,
        @RequestHeader("Refresh-Token") refreshToken: String
    ) = authFacade.reissue(ReissueCommand(req.accessToken, refreshToken)).let {
        ResponseEntity.ok(AuthResponse(it))
    }

    @DeleteMapping("/logout")
    @Operation(
        summary = "로그아웃",
        security = [SecurityRequirement(name = SecurityFilter.AUTHORIZATION_HEADER)],
    )
    fun signOut(@RequestHeader("Refresh-Token") refreshToken: String) =
        authFacade.signOut(SignOutCommand(refreshToken)).let {
            ResponseEntity.ok().build<Void>()
        }
}