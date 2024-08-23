package io.junseok.mealmate.domain.restaurantmenu.service;

import io.junseok.mealmate.domain.admin.repository.AdminRepository
import io.junseok.mealmate.domain.restaurant.repository.RestaurantRepository
import io.junseok.mealmate.domain.restaurantmenu.dto.request.MenuRegister
import io.junseok.mealmate.domain.restaurantmenu.entity.RestaurantMenu
import io.junseok.mealmate.domain.restaurantmenu.repository.RestaurantMenuRepository
import io.junseok.mealmate.exception.ErrorCode
import io.junseok.mealmate.exception.MealMateException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RestaurantMenuService(
    private val restaurantMenuRepository: RestaurantMenuRepository,
    private val restaurantRepository: RestaurantRepository,
    private val adminRepository: AdminRepository
) {
    @Transactional
    fun registerMenu(restaurantId: Long, menuList: List<MenuRegister>) {
        val restaurant = restaurantRepository.findByIdOrNull(restaurantId)
            ?: throw MealMateException(ErrorCode.NOT_EXIST_RESTAURANT)

        for (menu in menuList) {
            val restaurantMenu = RestaurantMenu(
                menuName = menu.menu,
                menuPrice = menu.price,
                restaurant = restaurant,
            )
            restaurantMenuRepository.save(restaurantMenu);
        }
    }

    @Transactional
    fun updateMenu(restaurantMenuId: Long, menuRegister: MenuRegister, email: String) {
        val admin = (adminRepository.findByEmail(email)
            ?: throw MealMateException(ErrorCode.NOT_EXIST_ADMIN))

        val restaurantMenu = (restaurantMenuRepository.findByIdOrNull(restaurantMenuId)
            ?: throw MealMateException(ErrorCode.NOT_EXIST_RESTAURANT_MENU))

        if(admin.restaurantName == restaurantMenu.restaurant.restaurantName){
            restaurantMenu.update(menuRegister.menu,menuRegister.price)
        }
    }

    @Transactional
    fun deleteMenu(restaurantMenuId: Long) {
        adminRepository.deleteById(restaurantMenuId)
    }
}
