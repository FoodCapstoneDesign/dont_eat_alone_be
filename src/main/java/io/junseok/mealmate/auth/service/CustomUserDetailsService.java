package io.junseok.mealmate.auth.service;

import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.member.repository.MemberRepository;
import io.junseok.mealmate.exception.ErrorCode;
import io.junseok.mealmate.exception.MealMateException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String email) {
        Member member = memberRepository.findOneWithAuthoritiesByEmail(email)
            .orElseThrow(() -> new MealMateException(ErrorCode.NOT_EXIST_ADMIN));
        return createAdmin(email,member);
    }

    private User createAdmin(String telNum, Member member) {
        if (!member.isActivated()) {
            throw new RuntimeException(telNum + " -> 활성화되어 있지 않습니다.");
        }
        GrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(member.getAuthority().toString());

        return new User(member.getEmail(),
            member.getPassword(),
            Set.of(grantedAuthorities));
    }
}