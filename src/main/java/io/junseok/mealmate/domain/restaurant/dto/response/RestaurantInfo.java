package io.junseok.mealmate.domain.restaurant.dto.response;

import io.junseok.mealmate.domain.restaurant.entity.Restaurant;
import lombok.Builder;

@Builder
public record RestaurantInfo(
    String restaurantName,
    String restaurantImageUrl,
    String restaurantType,
    int likeCount) {

    public static RestaurantInfo fromEntity(Restaurant restaurant){
        return RestaurantInfo.builder()
            .restaurantName(restaurant.getRestaurantName())
            .restaurantImageUrl(restaurant.getRestaurantImageUrl())
            .restaurantType(restaurant.getRestaurantType())
            .likeCount(restaurant.getLikeCount())
            .build();
    }
}
