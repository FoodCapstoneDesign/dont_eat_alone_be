package io.junseok.mealmate.exception

class MealMateException(
    var errorCode: ErrorCode
) : RuntimeException()