package io.junseok.mealmate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MealMateException extends RuntimeException{
    ErrorCode errorCode;
}
