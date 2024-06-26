package io.junseok.mealmate.domain.restaurantmenu.dto.response;

import io.junseok.mealmate.domain.restaurantmenu.entity.RestaurantMenu;
import lombok.Builder;

@Builder
public record MenuInfo(
    String menuName,
    String menuPrice
    ) {

    public static MenuInfo fromEntity(RestaurantMenu restaurantMenu){
        return MenuInfo.builder()
            .menuName(restaurantMenu.getMenuName())
            .menuPrice(restaurantMenu.getMenuPrice())
            .build();
    }
}
