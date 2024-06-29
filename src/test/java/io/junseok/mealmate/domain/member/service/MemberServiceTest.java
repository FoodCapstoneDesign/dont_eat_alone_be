/*
package io.junseok.mealmate.domain.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.junseok.mealmate.domain.member.dto.request.ModifyMemberInfo;
import io.junseok.mealmate.domain.member.dto.request.SignUpDto;
import io.junseok.mealmate.domain.member.dto.response.MemberInfoDto;
import io.junseok.mealmate.domain.member.entity.Authority;
import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.member.repository.MemberRepository;
import io.junseok.mealmate.domain.membercategory.dto.request.CategoryRegister;
import io.junseok.mealmate.domain.membercategory.service.MemberCategoryService;
import io.junseok.mealmate.exception.MealMateException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName(value = "Member Service Test")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks
    MemberService memberService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder encoder;
    @Mock
    MemberCategoryService categoryService;
    @Mock
    Member member;

    @Test
    @DisplayName("멤버_회원가입_테스트")
    void 멤버_회원가입_테스트() {
        //given
        CategoryRegister categoryRegister = new CategoryRegister("중식");
        List<CategoryRegister> list = List.of(categoryRegister);

        SignUpDto signUpDto = new SignUpDto(
            "wnstjr1204@naver.com",
            "password",
            list
        );

        when(memberRepository.save(any(Member.class))).thenAnswer(result ->{
            Member member = result.getArgument(0);
            Field field = member.getClass().getDeclaredField("memberId");
            field.setAccessible(true);
            field.set(member,1L);
            return member;
        });

        //when
        Long memberId = memberService.createMember(signUpDto);

        //then
        assertEquals(memberId,1L);
    }

    @Test
    @DisplayName("멤버_회원정보_노출_테스트")
    void 멤버_회원정보_노출_테스트() {

        //given
        String email = "test1@naver.com";
        Member mockMember = getMember();
        //Mock을 통해 생성된 Member객체를 생성
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(mockMember));

        //when
        MemberInfoDto result = memberService.showMemberInfo(email);

        //then
        assertEquals(email,result.email());
    }

    @Test
    @DisplayName("이메일_유효성_검사")
    void 이메일_유효성_검사() {
        //given
        String email = "test1@naver.com";

        when(memberRepository.existsByEmail(email)).thenReturn(true);

        //then
        //이미 존재하는 이메일인지 검사
        assertThrows(MealMateException.class, () -> {
            memberService.validEmail(email);
        });

        //이메일 형식 확인
        assertThrows(MealMateException.class,()->{
            memberService.validEmail(email);
        });
    }

    @Test
    @DisplayName("멤버_회원탈퇴_테스트")
    void 멤버_회원탈퇴_테스트() {
        //given
        Member member = getMember();
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));

        //when
        memberService.resignMember(member.getEmail());
        //then
        verify(memberRepository).delete(member); // return 값이 void, save, delete처럼 void 인 경우 시용
    }

    @Test
    @DisplayName("멤버_회원정보_수정")
    void 멤버_회원정보_수정() {
        //given
        String encodedPassword = "0000";
        Member member1 = getMember();
        CategoryRegister categoryRegister = new CategoryRegister("hi");
        List<CategoryRegister> list = List.of(categoryRegister);

        ModifyMemberInfo memberInfo = new ModifyMemberInfo("0000",list); //수정 내용

        when(memberRepository.findByEmail(member1.getEmail())).thenReturn(Optional.of(member1));
        when(encoder.encode(memberInfo.password)).thenReturn("0000");

        //when
        memberService.update(memberInfo,member1.getEmail());

        //then
        assertEquals(encodedPassword,member1.getPassword());
        verify(categoryService).updateMemberCategory(list,member1);
    }

    @NotNull
    private static Member getMember() {
        return new Member(
            1L,
            "test1@naver.com",
            "1234",
            true,
            Authority.ROLE_USER
        );
    }
}*/
