package io.junseok.mealmate.domain.membercategory.repository;

import io.junseok.mealmate.domain.membercategory.entity.MemberCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory,Long> {

}
