package io.junseok.mealmate.domain.restaurant.dto.request;

import java.time.LocalDateTime;

public record RestaurantRegister(
    String restaurantName,
    String restaurantType,
    String restaurantNotice,
    String location,
    String telNum,
    String openAt,
    String closeAt) {

}
