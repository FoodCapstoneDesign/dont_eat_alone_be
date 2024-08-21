package io.junseok.mealmate.domain.member.service

import io.junseok.mealmate.domain.member.dto.request.ModifyMemberInfo
import io.junseok.mealmate.domain.member.dto.request.SignUpDto
import io.junseok.mealmate.domain.member.dto.request.toCategoryRegisters
import io.junseok.mealmate.domain.member.dto.response.MemberInfoDto
import io.junseok.mealmate.domain.member.entity.Authority
import io.junseok.mealmate.domain.member.entity.Member
import io.junseok.mealmate.domain.member.repository.MemberRepository
import io.junseok.mealmate.domain.membercategory.service.MemberCategoryService
import io.junseok.mealmate.exception.ErrorCode
import io.junseok.mealmate.exception.MealMateException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.regex.Pattern

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberCategoryService: MemberCategoryService,
    private val encoder: PasswordEncoder
) {
    @Transactional
    fun createMember(signUpDto: SignUpDto): Long? {
        val member = Member(
            email = signUpDto.email,
            password = encoder.encode(signUpDto.password),
            nickname = signUpDto.nickname,
            school = signUpDto.school,
            studentNumber = signUpDto.studentNumber,
            department = signUpDto.department,
            activated = true,
            authority = Authority.ROLE_USER
        )

        memberCategoryService.saveCategory(signUpDto.categoryRegisters.toCategoryRegisters(), member)
        return memberRepository.save(member).memberId
    }

    fun showMemberInfo(email: String): MemberInfoDto {
        val member = getMember(email)
        return MemberInfoDto(
            email = member.email,
            password = member.password,
            nickname = member.nickname,
            school = member.school,
            studentNumber = member.studentNumber,
            department = member.department
        )
    }

    fun getMember(email: String): Member {
        return memberRepository.findByEmail(email)
            ?: throw MealMateException(ErrorCode.NOT_EXIST_ADMIN)
    }

    @Transactional
    fun resignMember(email: String) {
        val member = getMember(email)
        memberRepository.delete(member)
    }

    fun validEmail(email: String) {
        if (memberRepository.existsByEmail(email)) {
            throw MealMateException(ErrorCode.EXIST_EMAIL)
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw MealMateException(ErrorCode.INVALID_EMAIL_FORMAT)
        }
    }

    @Transactional
    fun update(modifyMemberInfo: ModifyMemberInfo, email: String) {
        val member = getMember(email)
        val encodePassword = encoder.encode(modifyMemberInfo.password)
        member.updatePassword(encodePassword)
        memberCategoryService.updateMemberCategory(modifyMemberInfo.categoryRegisters, member)
    }

    companion object{
        private val EMAIL_PATTERN: Pattern =
            Pattern.compile("^[a-zA-Z0-9._%+-]{2,}+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    }
}
