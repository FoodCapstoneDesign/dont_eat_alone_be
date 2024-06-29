package io.junseok.mealmate.domain.memberwish.repository;

import io.junseok.mealmate.domain.member.entity.Member
import io.junseok.mealmate.domain.memberwish.entity.MemberWish
import io.junseok.mealmate.domain.restaurant.entity.Restaurant
import org.springframework.data.jpa.repository.JpaRepository

interface WishRepository : JpaRepository<MemberWish,Long> {

    fun existsByRestaurantAndMember(restaurant: Restaurant, member: Member): Boolean
    fun findAllByMember(member: Member): List<MemberWish>
    fun countByMember(member: Member): Int
    fun deleteByRestaurantAndMember(restaurant: Restaurant,member: Member): Unit
}
