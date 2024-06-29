package io.junseok.mealmate.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MealMateException::class)
    protected fun errorCodeResponseEntity(ex: MealMateException): ResponseEntity<ErrorResponseEntity> =
        ex.errorCode.responseEntity()
}
