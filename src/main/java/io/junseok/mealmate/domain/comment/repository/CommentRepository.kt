package io.junseok.mealmate.domain.comment.repository;

import io.junseok.mealmate.domain.board.entity.Board;
import io.junseok.mealmate.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByBoard(board: Board): List<Comment>
}
