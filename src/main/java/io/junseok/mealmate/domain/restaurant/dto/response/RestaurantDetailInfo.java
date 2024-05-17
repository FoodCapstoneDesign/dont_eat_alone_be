package io.junseok.mealmate.domain.restaurant.dto.response;

import io.junseok.mealmate.domain.restaurantmenu.dto.response.MenuInfo;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record RestaurantDetailInfo(
    String restaurantName,
    String restaurantImageUrl,
    String restaurantType,
    int likeCount,
    double grade,
    LocalDateTime openAt,
    LocalDateTime closeAt,
    String restaurantNotice,
    List<MenuInfo> menuList) {

}
