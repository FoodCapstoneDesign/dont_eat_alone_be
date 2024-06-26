package io.junseok.mealmate.domain.member.repository

import io.junseok.mealmate.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByEmail(email: String): Boolean

    fun findOneWithAuthoritiesByEmail(email: String): Member?

    fun findByEmail(email: String): Member?
}
