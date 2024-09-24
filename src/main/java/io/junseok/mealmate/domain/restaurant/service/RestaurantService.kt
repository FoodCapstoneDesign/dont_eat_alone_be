package io.junseok.mealmate.domain.restaurant.service

import io.junseok.mealmate.domain.restaurant.dto.request.RestaurantRegister
import io.junseok.mealmate.domain.restaurant.dto.response.RestaurantDetailInfo
import io.junseok.mealmate.domain.restaurant.dto.response.RestaurantInfo
import io.junseok.mealmate.domain.restaurant.dto.response.toCreateRestaurantResponse
import io.junseok.mealmate.domain.restaurant.entity.Restaurant
import io.junseok.mealmate.domain.restaurant.repository.QueryRestaurantRepository
import io.junseok.mealmate.domain.restaurant.repository.RestaurantRepository
import io.junseok.mealmate.domain.restaurantmenu.dto.response.MenuInfo
import io.junseok.mealmate.domain.restaurantmenu.dto.response.toCreateMenuInfo
import io.junseok.mealmate.domain.restaurantmenu.entity.RestaurantMenu
import io.junseok.mealmate.domain.restaurantmenu.repository.RestaurantMenuRepository
import io.junseok.mealmate.exception.ErrorCode
import io.junseok.mealmate.exception.MealMateException
import io.junseok.mealmate.global.s3.service.S3UploadImage
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile


@Service
class RestaurantService(
    private val restaurantRepository: RestaurantRepository,
    private val restaurantMenuRepository: RestaurantMenuRepository,
    private val queryRestaurantRepository: QueryRestaurantRepository,
    private val s3UploadImage: S3UploadImage
) {
    @Transactional
    fun createRestaurant(restaurantRegister: RestaurantRegister, file: MultipartFile): Long? {
        if (restaurantRepository.existsByRestaurantName(restaurantRegister.restaurantName)) {
            throw MealMateException(ErrorCode.EXIST_EMAIL)
        }
        val s3Response =  s3UploadImage.saveImage(file)
        val restaurant = Restaurant(
            restaurantName = restaurantRegister.restaurantName,
            restaurantImageUrl = s3Response.imageUrl,
            restaurantFileName = s3Response.fileName,
            restaurantType = restaurantRegister.restaurantType,
            likeCount = INITIAL_COUNT,
            location = restaurantRegister.location,
            telNum = restaurantRegister.telNum,
            openAt = restaurantRegister.openAt,
            closeAt = restaurantRegister.closeAt,
            restaurantNotice = restaurantRegister.restaurantNotice,
            grade = INITIAL_GRADE
        )
        return restaurantRepository.save(restaurant).restaurantId
    }

    @Transactional
    fun removeRestaurant(restaurantId: Long) {
        restaurantRepository.deleteById(restaurantId)
    }

    /** TODO
     * Change TO nullable
     */
    @Transactional(readOnly = true)
    fun findRestaurants(restaurantType: String?): List<RestaurantInfo>? {
        return restaurantType.let { queryRestaurantRepository.findAllRestaurant(restaurantType!!)
            ?.map { it.toCreateRestaurantResponse()!! } } ?: restaurantRepository.findAll()
                .map { it.toCreateRestaurantResponse()!! }
    }

    fun showBestRestaurants(): List<RestaurantInfo> =
        restaurantRepository.findTop3ByOrderByLikeCountDesc()
            .map { it.toCreateRestaurantResponse()!! }

    @Transactional(readOnly = true)
    fun findRestaurantInfo(restaurantId: Long): RestaurantDetailInfo {
        val restaurant = restaurantRepository.findByIdOrNull(restaurantId)
            ?: throw MealMateException(ErrorCode.EXIST_EMAIL)

        val menuInfoList = restaurantMenuRepository.findAllByRestaurant(restaurant)
            .map { it.toCreateMenuInfo() }

        return RestaurantDetailInfo(
            restaurantName = restaurant.restaurantName,
            restaurantImageUrl = restaurant.restaurantImageUrl,
            restaurantType = restaurant.restaurantType,
            restaurantNotice = restaurant.restaurantNotice,
            restaurantTelNum = restaurant.telNum,
            location = restaurant.location,
            likeCount = restaurant.likeCount,
            grade = restaurant.grade,
            openAt = restaurant.openAt,
            closeAt = restaurant.closeAt,
            menuList = menuInfoList
        )
    }

    fun findByRestaurantId(restaurantId: Long): Restaurant =
        restaurantRepository.findById(restaurantId)
            .orElseThrow { MealMateException(ErrorCode.NOT_EXIST_RESTAURANT) }

    @Transactional(readOnly = true)
    fun findRestaurantByName(restaurantName: String): RestaurantInfo {
        return restaurantRepository.findByRestaurantName(restaurantName)?.toCreateRestaurantResponse()
            ?: throw MealMateException(ErrorCode.NOT_EXIST_RESTAURANT)
    }

    companion object {
        private const val INITIAL_COUNT = 0
        private const val INITIAL_GRADE = 0.0
    }
}
