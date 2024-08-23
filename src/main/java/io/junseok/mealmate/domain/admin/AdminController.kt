package io.junseok.mealmate.domain.admin

import io.junseok.mealmate.domain.admin.dto.request.AdminRequest
import io.junseok.mealmate.domain.admin.service.AdminService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RequestMapping("/api/admin")
@RestController
@CrossOrigin
class AdminController(private val adminService: AdminService) {
    //관리자 용 회원가입 API
    @PostMapping("/signup")
    fun adminSignUp(@RequestBody adminRequest: AdminRequest):ResponseEntity <Void>{
        adminService.createAdmin(adminRequest)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping
    fun adminDelete(principal: Principal): ResponseEntity<Unit>{
        adminService.deleteAdmin(principal.name)
        return ResponseEntity.ok().build()
    }
}