package io.junseok.mealmate.domain.restaurantmenu.presentation;

import io.junseok.mealmate.domain.restaurantmenu.dto.request.MenuRegister;
import io.junseok.mealmate.domain.restaurantmenu.service.RestaurantMenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant-menu")
@CrossOrigin
public class RestaurantMenuController {
    private final RestaurantMenuService restaurantMenuService;

    @PostMapping("/{restaurantId}")
    public ResponseEntity<Void> createMenu(
        @PathVariable Long restaurantId,
        @RequestBody List<MenuRegister> menuList){
        restaurantMenuService.registerMenu(restaurantId,menuList);
        return ResponseEntity.ok().build();
    }
}
