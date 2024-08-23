package io.junseok.mealmate.domain.admin.entity

import io.junseok.mealmate.domain.member.entity.Authority
import javax.persistence.*

@Entity
@Table(name = "admin")
class Admin(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    var adminId: Long? = null,

    @Column(name = "email")
    var email: String,

    @Column(name = "restaurant_name")
    var restaurantName: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "activated")
    var activated: Boolean,

    @Enumerated(EnumType.STRING)
    val authority: Authority

) {
}