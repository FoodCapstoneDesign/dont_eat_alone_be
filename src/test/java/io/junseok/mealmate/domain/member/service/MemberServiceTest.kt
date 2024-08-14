/*
package io.junseok.mealmate.domain.member.service

import io.junseok.mealmate.domain.member.dto.request.MemberCategoryRequest
import io.junseok.mealmate.domain.member.dto.request.ModifyMemberInfo
import io.junseok.mealmate.domain.member.dto.request.SignUpDto
import io.junseok.mealmate.domain.member.dto.response.MemberInfoDto
import io.junseok.mealmate.domain.member.entity.Authority
import io.junseok.mealmate.domain.member.entity.Member
import io.junseok.mealmate.domain.member.repository.MemberRepository
import io.junseok.mealmate.domain.membercategory.dto.request.CategoryRegister
import io.junseok.mealmate.domain.membercategory.service.MemberCategoryService
import io.junseok.mealmate.exception.ErrorCode
import io.junseok.mealmate.exception.MealMateException
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder

@DisplayName(value = "Member Service Test")
@ExtendWith(MockKExtension::class)
internal class MemberServiceTest {
    @InjectMockKs
    lateinit var memberService: MemberService

    @MockK
    lateinit var memberCategoryService: MemberCategoryService

    @RelaxedMockK
    lateinit var memberRepository: MemberRepository

    @MockK
    lateinit var encoder: PasswordEncoder

    @MockK
    lateinit var member: Member
    @Test
    @DisplayName("멤버_회원가입_테스트")
    fun 멤버_회원가입_테스트() {
        val signUpDto = SignUpDto(
            email = "kelly.hardin@example.com", password = "turpis", categoryRegisters = listOf(
                MemberCategoryRequest("중식을좋아함")
            )
        )
        val encodedPassword = "new Password"
        val member = Member(
            email = signUpDto.email,
            password = signUpDto.password,
            activated = true,
            authority = Authority.ROLE_USER
        ).apply { memberId = 1L }

        every { encoder.encode(signUpDto.password) } returns encodedPassword
        every { memberRepository.save(any()) } returns member
        every { memberCategoryService.saveCategory(any(), any()) } returns Unit
        val createMember = memberService.createMember(signUpDto)

        assertEquals(member.memberId, createMember)
        verify(exactly = 1) { encoder.encode(signUpDto.password) }
    }

    @Test
    @DisplayName("멤버_회원정보_노출_테스트")
    fun 멤버_회원정보_노출_테스트() {
        val member = Member(
            email = "signUpDtoemail@naver.com",
            password = "password",
            activated = true,
            authority = Authority.ROLE_USER
        ).apply { memberId = 1L }

        every { memberRepository.findByEmail(any()) } returns member

        val memberInfoDto = MemberInfoDto(
            email = member.email,
            password = member.password
        )

        val showMemberInfo = memberService.showMemberInfo(member.email)
        assertEquals(memberInfoDto.email, showMemberInfo.email)
        assertEquals(memberInfoDto.password, showMemberInfo.password)
    }

    @Test
    @DisplayName("이메일_유효성_검사")
    fun 이메일_유효성_검사() {
        val member = Member(
            email = "signUpDtoemail@naver.com",
            password = "password",
            activated = true,
            authority = Authority.ROLE_USER
        ).apply { memberId = 1L }

        every { memberRepository.existsByEmail(any()) } returns true
        val assertThrows =
            assertThrows<MealMateException> { memberService.validEmail(member.email) }

        assertEquals(assertThrows.errorCode, ErrorCode.EXIST_EMAIL)
    }

    @Test
    @DisplayName("멤버_회원탈퇴_테스트")
    fun 멤버_회원탈퇴_테스트() {
        val member = Member(
            email = "signUpDtoemail@naver.com",
            password = "password",
            activated = true,
            authority = Authority.ROLE_USER
        ).apply { memberId = 1L }
        every { memberService.getMember(any()) } returns member
        every { memberRepository.delete(member) } returns Unit

        memberService.resignMember(member.email)

        verify(exactly = 1) { memberRepository.findByEmail(member.email) }
        verify(exactly = 1) { memberRepository.delete(member) }
    }

    @Test
    @DisplayName("멤버_회원정보_수정")
    fun 멤버_회원정보_수정() {
        // given
        val member = getMember()
        val newPassword = "new password"
        val modifyMemberInfo = ModifyMemberInfo(password = "molestie", categoryRegisters = listOf(
            CategoryRegister(
                categoryName = "분노"
            )
        ))

        // Mocking 설정
        every { encoder.encode(modifyMemberInfo.password) } returns newPassword
        every { memberCategoryService.updateMemberCategory(any(), any()) } returns Unit
        member.password = newPassword
        // when
        memberService.update(modifyMemberInfo, member.email)

        // then
        assertEquals(newPassword, member.password) // 비밀번호가 업데이트 되었는지 확인
        verify { encoder.encode(modifyMemberInfo.password) }
        verify { memberCategoryService.updateMemberCategory(any(), any()) }
    }

    companion object {
        private fun getMember(): Member {
            return Member(
                1L,
                "test1@naver.com",
                "1234",
                true,
                Authority.ROLE_USER
            )
        }
    }
}
*/
