package io.junseok.mealmate.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MealMateException.class)
    protected ResponseEntity<ErrorResponseEntity> errorCodeResponseEntity(MealMateException ex){
        return ErrorResponseEntity.responseEntity(ex.getErrorCode());
    }
}
