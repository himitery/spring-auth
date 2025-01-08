package dev.himitery.spring_auth.modules.auth.adapter.`in`.rest

import dev.himitery.spring_auth.modules.auth.domain.exception.InvalidTokenException
import dev.himitery.spring_auth.modules.auth.domain.exception.LoginIdNotFoundException
import dev.himitery.spring_auth.modules.auth.domain.exception.PasswordMismatchException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthControllerAdvice {

    @ExceptionHandler(InvalidTokenException::class)
    fun invalidTokenException(e: InvalidTokenException) =
        ResponseEntity.status(401).body(e.message)

    @ExceptionHandler(LoginIdNotFoundException::class)
    fun loginIdNotFoundException(e: LoginIdNotFoundException) =
        ResponseEntity.status(404).body(e.message)

    @ExceptionHandler(PasswordMismatchException::class)
    fun passwordMismatchException(e: PasswordMismatchException) =
        ResponseEntity.status(404).body(e.message)
}