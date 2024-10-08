package io.junseok.mealmate.domain.restaurant.dto.response;

import io.junseok.mealmate.domain.restaurant.entity.Restaurant


data class RestaurantInfo(
    val restaurantName: String,
    val restaurantImageUrl: String,
    val restaurantType: String,
    val likeCount: Int,
    val restaurantId: Long,
    val openAt: String,
    val closeAt: String,
)

fun Restaurant.toCreateRestaurantResponse() = this.restaurantId?.let {
    RestaurantInfo(
        restaurantName = this.restaurantName,
        restaurantImageUrl = this.restaurantImageUrl,
        restaurantType = this.restaurantType,
        likeCount = this.likeCount,
        restaurantId = it,
        openAt = this.openAt,
        closeAt = this.closeAt,
    )
}
