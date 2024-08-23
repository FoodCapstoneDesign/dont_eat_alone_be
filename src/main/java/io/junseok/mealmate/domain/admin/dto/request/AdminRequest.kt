package io.junseok.mealmate.domain.admin.dto.request

data class AdminRequest(
    val email: String,
    val restaurantName: String,
    val password: String,
)
