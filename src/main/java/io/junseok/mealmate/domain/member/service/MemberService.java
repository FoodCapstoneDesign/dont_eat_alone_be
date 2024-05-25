package io.junseok.mealmate.domain.member.service;

import static io.junseok.mealmate.exception.ErrorCode.INVALID_EMAIL_FORMAT;

import io.junseok.mealmate.domain.member.dto.request.SignUpDto;
import io.junseok.mealmate.domain.member.dto.response.MemberInfoDto;
import io.junseok.mealmate.domain.member.entity.Authority;
import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.member.repository.MemberRepository;
import io.junseok.mealmate.exception.ErrorCode;
import io.junseok.mealmate.exception.MealMateException;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[a-zA-Z0-9._%+-]{2,}+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    @Transactional
    public Long createMember(SignUpDto signUpDto) {

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

    public void validEmail(String email) {
        if(memberRepository.existsByEmail(email)){
            throw new MealMateException(ErrorCode.EXIST_EMAIL);
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new MealMateException(INVALID_EMAIL_FORMAT);
        }
    }
}
