package io.junseok.mealmate.domain.restaurant.dto.response;

import io.junseok.mealmate.domain.restaurantmenu.dto.response.MenuInfo

data class RestaurantDetailInfo(
    val restaurantName: String,
    val restaurantImageUrl: String,
    val restaurantType: String,
    val restaurantTelNum: String,
    val location: String,
    val likeCount: Int,
    val grade: Double,
    val openAt: String,
    val closeAt: String,
    val restaurantNotice: String,
    val menuList: List<MenuInfo>
)
