package io.junseok.mealmate.domain.memberwish.presentation

import io.junseok.mealmate.domain.memberwish.dto.response.MemberWishList
import io.junseok.mealmate.domain.memberwish.service.WishService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/wish")
@CrossOrigin
class WishController(
    private val wishService: WishService
) {

    @GetMapping("/{restaurantId}")
    fun saveWishList(
        @PathVariable restaurantId: Long,
        principal: Principal
    ): ResponseEntity<Long> =
        ResponseEntity.ok(wishService.createWishList(restaurantId, principal.name))


    @GetMapping
    fun getMemberWishLists(principal: Principal): ResponseEntity<MemberWishList> =
        ResponseEntity.ok(wishService.getWishList(principal.name))

    @DeleteMapping("/{restaurantId}")
    fun deleteWishList(
        @PathVariable restaurantId: Long,
        principal: Principal
    ): ResponseEntity<Unit> {
        wishService.remove(restaurantId, principal.name)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/count")
    fun getWishListCount(principal: Principal): ResponseEntity<Int> =
        ResponseEntity.ok(wishService.showWishListCount(principal.name))
}
