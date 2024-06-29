package io.junseok.mealmate.auth.presentation

import io.junseok.mealmate.auth.dto.LoginDto
import io.junseok.mealmate.auth.service.LoginAuthService
import org.jetbrains.annotations.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
@CrossOrigin
class AuthController(
    private val loginAuthService: LoginAuthService
) {
    @PostMapping("/login")
    fun authorize(@RequestBody loginDto: LoginDto) =
        loginAuthService.login(loginDto).also {
            ResponseEntity.ok(it)
        }
}