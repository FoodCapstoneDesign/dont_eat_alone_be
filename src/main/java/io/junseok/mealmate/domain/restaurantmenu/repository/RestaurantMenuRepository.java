package io.junseok.mealmate.domain.restaurantmenu.repository;

import io.junseok.mealmate.domain.restaurant.entity.Restaurant;
import io.junseok.mealmate.domain.restaurantmenu.entity.RestaurantMenu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu,Long> {
    List<RestaurantMenu> findAllByRestaurant(Restaurant restaurant);
}
