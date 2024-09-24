package io.junseok.mealmate.domain.memberwish.dto.response;

import io.junseok.mealmate.domain.memberwish.entity.MemberWish
import io.junseok.mealmate.domain.restaurant.dto.response.RestaurantInfo


data class MemberWishList(val restaurantInfoList: List<RestaurantInfo>)

fun MemberWish.toCreateRestaurantInfo() = RestaurantInfo(
    restaurantId = this.restaurant.restaurantId!!,
    restaurantName = this.restaurant.restaurantName,
    restaurantImageUrl = this.restaurant.restaurantImageUrl,
    restaurantType = this.restaurant.restaurantType,
    likeCount = this.restaurant.likeCount,
    openAt = this.restaurant.openAt,
    closeAt = this.restaurant.closeAt
)
