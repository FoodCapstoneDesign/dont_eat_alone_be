package io.junseok.mealmate.domain.restaurant.dto.request;

data class RestaurantRegister(
    val restaurantName: String,
    val restaurantType: String,
    val restaurantNotice: String,
    val location: String,
    val telNum: String,
    val openAt: String,
    val closeAt: String
)
