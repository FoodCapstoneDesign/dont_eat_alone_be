package io.junseok.mealmate.domain.member.service;

import io.junseok.mealmate.domain.member.dto.request.SignUpDto;
import io.junseok.mealmate.domain.member.dto.response.MemberInfoDto;
import io.junseok.mealmate.domain.member.entity.Authority;
import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.member.repository.MemberRepository;
import io.junseok.mealmate.exception.ErrorCode;
import io.junseok.mealmate.exception.MealMateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Long createMember(SignUpDto signUpDto) {
        if(memberRepository.existsByEmail(signUpDto.email())){
            throw new MealMateException(ErrorCode.EXIST_EMAIL);
        }

        Member member = Member.builder()
            .email(signUpDto.email())
            .password(encoder.encode(signUpDto.password()))
            .activated(true)
            .authority(Authority.ROLE_USER)
            .build();

        return memberRepository.save(member).getMemberId();
    }

    public MemberInfoDto showMemberInfo(String email) {
        Member member = getMember(email);
        return MemberInfoDto.builder()
            .email(member.getEmail())
            .build();
    }

    public Member getMember(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new MealMateException(ErrorCode.NOT_EXIST_ADMIN));
    }

    @Transactional
    public void resignMember(String email) {
        Member member = getMember(email);
        memberRepository.delete(member);
    }
}
