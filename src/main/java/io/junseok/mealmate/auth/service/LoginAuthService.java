package io.junseok.mealmate.auth.service;

import io.junseok.mealmate.auth.dto.LoginDto;
import io.junseok.mealmate.auth.dto.TokenDto;
import io.junseok.mealmate.auth.jwt.TokenProvider;
import io.junseok.mealmate.domain.member.repository.MemberRepository;
import io.junseok.mealmate.exception.ErrorCode;
import io.junseok.mealmate.exception.MealMateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class LoginAuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    public TokenDto login(LoginDto loginDto) {
        if (!memberRepository.existsByEmail(loginDto.getEmail())) {
            throw new MealMateException(ErrorCode.EXIST_EMAIL);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        String jwt = tokenProvider.createToken(authentication);

        return new TokenDto(jwt);
    }
}
