package io.junseok.mealmate.domain.restaurant.presentation

import io.junseok.mealmate.domain.restaurant.dto.request.RestaurantRegister
import io.junseok.mealmate.domain.restaurant.dto.response.RestaurantDetailInfo
import io.junseok.mealmate.domain.restaurant.dto.response.RestaurantInfo
import io.junseok.mealmate.domain.restaurant.service.RestaurantService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/restaurant")
@CrossOrigin
class RestaurantController(
    private val restaurantService: RestaurantService
) {
    /**
     * 식당 정보 등록
     */
    @PostMapping(value = [""], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun registerRestaurant(
        @RequestPart(value = "imageFile", required = true) imageFile: MultipartFile,
        @RequestPart(value = "restaurantRegister") restaurantRegister: RestaurantRegister
    ): ResponseEntity<Long?> =
        ResponseEntity.ok(restaurantService.createRestaurant(restaurantRegister, imageFile))

    /**
     * 식당 정보 삭제
     */
    @DeleteMapping("/{restaurantId}")
    fun deleteRestaurant(@PathVariable restaurantId: Long): ResponseEntity<Unit> {
        restaurantService.removeRestaurant(restaurantId)
        return ResponseEntity.ok().build()
    }

    /**
     * 검색한 타입에 맞는 식당 목록 반환
     */
    @GetMapping
    fun getRestaurantList(
        @RequestParam(value = "type", required = false) restaurantType: String
    ): ResponseEntity<List<RestaurantInfo>?> =
        ResponseEntity.ok(restaurantService.findRestaurants(restaurantType))

    @GetMapping("/best")
    fun bestRestaurantList(): ResponseEntity<List<RestaurantInfo>> =
        ResponseEntity.ok(restaurantService.showBestRestaurants())

    /**
     * 식당 세부정보 조회
     */
    @GetMapping("/{restaurantId}")
    fun getRestaurantInfo(@PathVariable restaurantId: Long): ResponseEntity<RestaurantDetailInfo> =
        ResponseEntity.ok(restaurantService.findRestaurantInfo(restaurantId))
}
