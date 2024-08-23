package io.junseok.mealmate.domain.admin.repository

import io.junseok.mealmate.domain.admin.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin,Long> {
    fun findByEmail(email: String):Admin?
}