package io.junseok.mealmate.domain.membercategory.repository

import io.junseok.mealmate.domain.member.entity.Member
import io.junseok.mealmate.domain.membercategory.entity.MemberCategory
import org.springframework.data.jpa.repository.JpaRepository

interface MemberCategoryRepository : JpaRepository<MemberCategory, Long> {
    fun findAllByMember(member: Member): List<MemberCategory>
}
