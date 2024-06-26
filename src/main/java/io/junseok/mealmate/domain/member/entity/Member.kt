package io.junseok.mealmate.domain.member.entity;


import io.junseok.mealmate.domain.base.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val memberId: Long? = null,

    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "activated")
    var activated: Boolean,

    @Enumerated(EnumType.STRING)
    val authority: Authority
) : BaseTimeEntity() {


    fun updatePassword(password: String): Unit {
        this.password = password
    }
}