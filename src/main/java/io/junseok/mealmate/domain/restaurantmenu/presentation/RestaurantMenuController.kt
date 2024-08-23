package io.junseok.mealmate.domain.restaurantmenu.presentation

import io.junseok.mealmate.domain.restaurantmenu.dto.request.MenuRegister
import io.junseok.mealmate.domain.restaurantmenu.service.RestaurantMenuService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/restaurant-menu")
@CrossOrigin
class RestaurantMenuController(
    private val restaurantMenuService: RestaurantMenuService
) {
    @PostMapping("/{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun createMenu(
        @PathVariable restaurantId: Long,
        @RequestBody menuList: List<MenuRegister>
    ): ResponseEntity<Unit> {
        restaurantMenuService.registerMenu(restaurantId, menuList)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/{restaurantMenuId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun modifyMenu(
        @PathVariable restaurantMenuId: Long,
        @RequestBody menuRegister: MenuRegister,
        principal: Principal
    ): ResponseEntity<Unit> {
        restaurantMenuService.updateMenu(
            restaurantMenuId,
            menuRegister,
            principal.name
        )
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{restaurantMenuId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun removeMenu(@PathVariable restaurantMenuId: Long, principal: Principal):ResponseEntity<Unit>{
        restaurantMenuService.deleteMenu(restaurantMenuId)
        return ResponseEntity.ok().build()
    }
}
