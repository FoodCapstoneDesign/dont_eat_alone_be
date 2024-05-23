package io.junseok.mealmate.domain.memberwish.repository;

import io.junseok.mealmate.domain.board.entity.Board;
import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.memberwish.entity.MemberWish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<MemberWish,Long> {
    boolean existsByBoardAndMember(Board board, Member member);
    List<MemberWish> findAllByMember(Member member);

    Integer countByMember(Member member);
}
