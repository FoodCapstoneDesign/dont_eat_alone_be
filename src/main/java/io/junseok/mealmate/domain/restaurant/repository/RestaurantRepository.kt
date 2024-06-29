package io.junseok.mealmate.domain.restaurant.repository

import io.junseok.mealmate.domain.restaurant.entity.Restaurant
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantRepository : JpaRepository<Restaurant, Long> {
    fun existsByRestaurantName(restaurantName: String): Boolean

    fun findAllByRestaurantType(restaurantType: String): List<Restaurant>?

    fun findTop3ByOrderByLikeCountDesc(): List<Restaurant>
}
