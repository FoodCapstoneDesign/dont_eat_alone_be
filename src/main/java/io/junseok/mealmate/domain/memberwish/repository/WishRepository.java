package io.junseok.mealmate.domain.memberwish.repository;

import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.memberwish.entity.MemberWish;
import io.junseok.mealmate.domain.restaurant.entity.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<MemberWish,Long> {
    boolean existsByRestaurantAndMember(Restaurant restaurant, Member member);
    List<MemberWish> findAllByMember(Member member);

    Integer countByMember(Member member);
}
