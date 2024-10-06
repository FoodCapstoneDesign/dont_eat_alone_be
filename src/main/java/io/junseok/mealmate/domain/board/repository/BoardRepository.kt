package io.junseok.mealmate.domain.board.repository;

import io.junseok.mealmate.domain.board.entity.Board;
import io.junseok.mealmate.domain.restaurant.entity.Restaurant
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository;

interface BoardRepository : JpaRepository<Board,Long> {
    fun findAllByOrderByCreateDtDesc(pageable: Pageable): Page<Board>

    fun findAllByRestaurantOrderByCreateDtDesc(pageable: Pageable,restaurant: Restaurant):Page<Board>
}
