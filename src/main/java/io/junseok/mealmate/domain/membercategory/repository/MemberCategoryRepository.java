package io.junseok.mealmate.domain.membercategory.repository;

import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.membercategory.entity.MemberCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory,Long> {
    List<MemberCategory> findAllByMember(Member member);
}
