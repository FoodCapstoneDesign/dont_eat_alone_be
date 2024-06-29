package io.junseok.mealmate.domain.restaurantmenu.repository;

import io.junseok.mealmate.domain.restaurant.entity.Restaurant
import io.junseok.mealmate.domain.restaurantmenu.entity.RestaurantMenu
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantMenuRepository : JpaRepository<RestaurantMenu, Long> {
    fun findAllByRestaurant(restaurant: Restaurant): List<RestaurantMenu>
}
