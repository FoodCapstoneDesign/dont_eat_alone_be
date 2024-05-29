package io.junseok.mealmate.domain.board.repository;

import io.junseok.mealmate.domain.board.entity.Board;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findAllByOrderByCreateDtDesc();
}