package io.junseok.mealmate.domain.member.presentation;


import io.junseok.mealmate.domain.member.dto.request.EmailCheck
import io.junseok.mealmate.domain.member.dto.request.ModifyMemberInfo
import io.junseok.mealmate.domain.member.dto.request.SignUpDto
import io.junseok.mealmate.domain.member.dto.response.MemberInfoDto
import io.junseok.mealmate.domain.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/member")
@CrossOrigin
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    fun signUp(@RequestBody signUpDto: SignUpDto): ResponseEntity<Long>{
        return ResponseEntity.ok(memberService.createMember(signUpDto));
    }

    @GetMapping
    fun getMemberInfo(principal: Principal): ResponseEntity<MemberInfoDto>{
        return ResponseEntity.ok(memberService.showMemberInfo(principal.name));
    }

    @DeleteMapping
    fun deleteMember(principal: Principal): ResponseEntity<Unit>{
        memberService.resignMember(principal.name);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/check-email")
    fun checkEmail(@RequestBody emailCheck: EmailCheck): ResponseEntity<Unit>{
        memberService.validEmail(emailCheck.email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    fun updateMemberInfo(
        @RequestBody modifyMemberInfo: ModifyMemberInfo,
        principal: Principal
        ): ResponseEntity<Unit> {
        memberService.update(modifyMemberInfo,principal.name)
        return ResponseEntity.ok().build();
    }
}
