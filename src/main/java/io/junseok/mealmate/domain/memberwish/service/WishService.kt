package io.junseok.mealmate.domain.memberwish.service;

import io.junseok.mealmate.domain.member.service.MemberService
import io.junseok.mealmate.domain.memberwish.dto.response.MemberWishList
import io.junseok.mealmate.domain.memberwish.dto.response.toCreateRestaurantInfo
import io.junseok.mealmate.domain.memberwish.entity.MemberWish
import io.junseok.mealmate.domain.memberwish.repository.WishRepository
import io.junseok.mealmate.domain.restaurant.service.RestaurantService
import io.junseok.mealmate.exception.ErrorCode
import io.junseok.mealmate.exception.MealMateException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WishService(
    private val wishRepository: WishRepository,
    private val memberService: MemberService,
    private val restaurantService: RestaurantService
) {
    @Transactional
    fun createWishList(restaurantId: Long, email: String): Long {
        val member = memberService.getMember(email)
        val restaurant = restaurantService.findByRestaurantId(restaurantId)

        if (wishRepository.existsByRestaurantAndMember(restaurant, member)) {
            throw MealMateException(ErrorCode.EXIST_WISHLIST);
        }

        val memberWish = MemberWish(
            restaurant = restaurant,
            member = member
        )
        return wishRepository.save(memberWish).memberWishId!!
    }

    @Transactional
    fun getWishList(email: String): MemberWishList {
        val member = memberService.getMember(email)
        val restaurantInfoList = wishRepository.findAllByMember(member)
            .map { it.toCreateRestaurantInfo() }
        return MemberWishList(
            restaurantInfoList = restaurantInfoList
        )
    }

    @Transactional
    fun remove(restaurantId: Long, email: String) {
        val member = memberService.getMember(email)
        val restaurant = restaurantService.findByRestaurantId(restaurantId)
        if (!wishRepository.existsByRestaurantAndMember(restaurant, member)) {
            throw MealMateException(ErrorCode.NOT_EXIST_WISHLIST)
        }

        wishRepository.deleteByRestaurantAndMember(restaurant, member);
    }

    @Transactional(readOnly = true)
    fun showWishListCount(email: String): Int {
        val member = memberService.getMember(email);
        return wishRepository.countByMember(member);
    }
}
