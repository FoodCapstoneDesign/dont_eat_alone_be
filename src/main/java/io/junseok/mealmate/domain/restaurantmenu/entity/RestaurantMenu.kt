package io.junseok.mealmate.domain.restaurantmenu.entity

import io.junseok.mealmate.domain.restaurant.entity.Restaurant
import javax.persistence.*

@Entity
@Table(name = "restaurant_menu")
class RestaurantMenu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_menu_id")
    val restaurantMenuId: Long? = null,

    @Column(name = "menu_name")
    var menuName: String,

    @Column(name = "menu_price")
    var menuPrice: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    val restaurant: Restaurant
)
