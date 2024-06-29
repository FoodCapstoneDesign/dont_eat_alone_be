package io.junseok.mealmate.domain.board.repository;

import io.junseok.mealmate.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

interface BoardRepository : JpaRepository<Board,Long> {
    fun  findAllByOrderByCreateDtDesc(): List<Board>
}
