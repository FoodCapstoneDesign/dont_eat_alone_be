package io.junseok.mealmate.domain.memberwish.entity

import io.junseok.mealmate.domain.member.entity.Member
import io.junseok.mealmate.domain.restaurant.entity.Restaurant
import javax.persistence.*

@Entity
@Table(name = "member_wish")
class MemberWish(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_wish_id")
    var memberWishId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    val restaurant: Restaurant
)
