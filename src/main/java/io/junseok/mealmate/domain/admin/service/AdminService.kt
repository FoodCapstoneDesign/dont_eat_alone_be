package io.junseok.mealmate.domain.admin.service

import io.junseok.mealmate.domain.admin.dto.request.AdminRequest
import io.junseok.mealmate.domain.admin.entity.Admin
import io.junseok.mealmate.domain.admin.repository.AdminRepository
import io.junseok.mealmate.domain.member.entity.Authority
import io.junseok.mealmate.exception.ErrorCode
import io.junseok.mealmate.exception.MealMateException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminService(
    private val adminRepository: AdminRepository,
    private val encoder: PasswordEncoder
) {
    @Transactional
    fun createAdmin(adminRequest: AdminRequest) {
        val admin = Admin(
            email = adminRequest.email,
            password = encoder.encode(adminRequest.password),
            restaurantName = adminRequest.restaurantName,
            activated = true,
            authority = Authority.ROLE_ADMIN
        )
        adminRepository.save(admin)
    }

    @Transactional
    fun deleteAdmin(email: String) {
        val admin = (adminRepository.findByEmail(email)
            ?: throw MealMateException(ErrorCode.NOT_EXIST_ADMIN))
        adminRepository.delete(admin)
    }
}