package io.junseok.mealmate.domain.restaurantmenu.dto.response;

import io.junseok.mealmate.domain.restaurantmenu.entity.RestaurantMenu;
import lombok.Builder;

data class MenuInfo(
    val menuName: String,
    val menuPrice: String
)

fun RestaurantMenu.toCreateMenuInfo() = MenuInfo(
    menuName = this.menuName,
    menuPrice = this.menuPrice
)

