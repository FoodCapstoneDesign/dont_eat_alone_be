package io.junseok.mealmate.domain.restaurant.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import io.junseok.mealmate.domain.restaurant.entity.QRestaurant
import io.junseok.mealmate.domain.restaurant.entity.Restaurant
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
@RequiredArgsConstructor
class QueryRestaurantRepository(
    private val em: EntityManager
) {
    var restaurant: QRestaurant = QRestaurant.restaurant
    fun findAllRestaurant(restaurantType: String): List<Restaurant>? {
        val queryFactory = JPAQueryFactory(em)


        return queryFactory.selectFrom(restaurant)
            .where(restaurantTypeLike(restaurantType))
            .fetch()
    }

    private fun restaurantTypeLike(restaurantType: String): BooleanExpression? {
        return if (restaurantType.isNotEmpty()) restaurant.restaurantType.eq(restaurantType) else null
    }
}
