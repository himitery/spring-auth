package dev.himitery.spring_auth.shared.filter

import dev.himitery.spring_auth.modules.auth.application.service.ValidateTokenUseCase
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter(
    private val validateTokenUseCase: ValidateTokenUseCase
) : OncePerRequestFilter() {

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        private const val TOKEN_PREFIX = "Bearer "
    }

    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        extractToken(req)?.let { token ->
            validateTokenUseCase.parseAccessToken(token)?.let { authId ->
                SecurityContextHolder.getContext().authentication = createAuthentication(authId)
            }
        }

        chain.doFilter(req, res)
    }

    private fun extractToken(req: HttpServletRequest): String? {
        return req.getHeader(AUTHORIZATION_HEADER)?.takeIf { it.startsWith(TOKEN_PREFIX) }
            ?.substring(TOKEN_PREFIX.length)
    }

    private fun createAuthentication(authId: String): AbstractAuthenticationToken {
        return UsernamePasswordAuthenticationToken(authId, null, emptyList())
    }
}