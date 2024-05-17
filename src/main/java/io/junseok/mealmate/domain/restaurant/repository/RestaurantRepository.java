package io.junseok.mealmate.domain.restaurant.repository;

import io.junseok.mealmate.domain.restaurant.entity.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    boolean existsByRestaurantName(String restaurantName);

    List<Restaurant> findAllByRestaurantType(String restaurantType);

    List<Restaurant> find3TopByLikeCount();
}
