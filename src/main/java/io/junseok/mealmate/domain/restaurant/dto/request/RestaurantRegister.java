package io.junseok.mealmate.domain.restaurant.dto.request;

import java.time.LocalDateTime;

public record RestaurantRegister(
    String restaurantName,
    String restaurantImageUrl,
    String restaurantFileName,
    String restaurantType,
    String restaurantNotice,
    String location,
    String telNum,
    LocalDateTime openAt,
    LocalDateTime closeAt) {

}
