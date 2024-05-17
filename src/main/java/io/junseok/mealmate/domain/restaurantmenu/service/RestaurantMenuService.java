package io.junseok.mealmate.domain.restaurantmenu.service;

import io.junseok.mealmate.domain.restaurant.entity.Restaurant;
import io.junseok.mealmate.domain.restaurant.repository.RestaurantRepository;
import io.junseok.mealmate.domain.restaurantmenu.dto.request.MenuRegister;
import io.junseok.mealmate.domain.restaurantmenu.entity.RestaurantMenu;
import io.junseok.mealmate.domain.restaurantmenu.repository.RestaurantMenuRepository;
import io.junseok.mealmate.exception.ErrorCode;
import io.junseok.mealmate.exception.MealMateException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantMenuService {
    private final RestaurantMenuRepository restaurantMenuRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void registerMenu(Long restaurantId, List<MenuRegister> menuList) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new MealMateException(ErrorCode.EXIST_EMAIL));

        for(MenuRegister menu : menuList){
            RestaurantMenu restaurantMenu = RestaurantMenu.builder()
                .menuName(menu.menu())
                .menuPrice(menu.price())
                .restaurant(restaurant)
                .build();
            restaurantMenuRepository.save(restaurantMenu);
        }
    }
}
