package io.junseok.mealmate.domain.comment.repository;

import io.junseok.mealmate.domain.board.entity.Board;
import io.junseok.mealmate.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByBoard(Board board);
}
