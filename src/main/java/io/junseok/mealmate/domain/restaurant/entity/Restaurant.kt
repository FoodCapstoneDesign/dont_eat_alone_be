package io.junseok.mealmate.domain.restaurant.entity;

import javax.persistence.*

@Entity
@Table(name = "restaurant")
class Restaurant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    val restaurantId: Long? = null,

    @Column(name = "restaurant_name")
    val restaurantName: String,

    @Column(name = "restaurant_image_url")
    var restaurantImageUrl: String,

    @Column(name = "restaurant_file_name")
    var restaurantFileName: String,

    @Column(name = "restaurant_type")
    val restaurantType: String,

    @Column(name = "restaurant_notice")
    var restaurantNotice: String,

    @Column(name = "location")
    val location: String,

    @Column(name = "tel_num")
    var telNum: String,

    @Column(name = "like_count")
    var likeCount: Int,

    @Column(name = "grade")
    var grade: Double,

    @Column(name = "open_at")
    val openAt: String,

    @Column(name = "close_at")
    val closeAt: String
)