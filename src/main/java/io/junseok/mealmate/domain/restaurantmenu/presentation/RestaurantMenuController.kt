package io.junseok.mealmate.domain.restaurantmenu.presentation

import io.junseok.mealmate.domain.restaurantmenu.dto.request.MenuRegister
import io.junseok.mealmate.domain.restaurantmenu.service.RestaurantMenuService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/restaurant-menu")
@CrossOrigin
class RestaurantMenuController(
    private val restaurantMenuService: RestaurantMenuService
) {
    @PostMapping("/{restaurantId}")
    fun createMenu(
        @PathVariable restaurantId: Long,
        @RequestBody menuList: List<MenuRegister>
    ): ResponseEntity<Unit> {
        restaurantMenuService.registerMenu(restaurantId, menuList)
        return ResponseEntity.ok().build()
    }
}
