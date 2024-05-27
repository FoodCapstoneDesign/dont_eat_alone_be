package io.junseok.mealmate.domain.restaurant.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.junseok.mealmate.domain.restaurant.entity.QRestaurant;
import io.junseok.mealmate.domain.restaurant.entity.Restaurant;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueryRestaurantRepository {
    private final EntityManager em;
    QRestaurant restaurant = QRestaurant.restaurant;
    public List<Restaurant> findAllRestaurant(String restaurantType){

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        return queryFactory.selectFrom(restaurant)
            .where(restaurantTypeLike(restaurantType))
            .fetch();
    }

    private BooleanExpression restaurantTypeLike(String restaurantType){
        return !restaurantType.isEmpty() ? restaurant.restaurantType.eq(restaurantType):null;
    }
}
