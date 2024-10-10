package io.junseok.mealmate.auth.service

import io.junseok.mealmate.auth.dto.LoginDto
import io.junseok.mealmate.auth.dto.TokenDto
import io.junseok.mealmate.auth.jwt.TokenProvider
import io.junseok.mealmate.domain.member.repository.MemberRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class LoginAuthService(
    private val tokenProvider: TokenProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val memberRepository: MemberRepository
) {
    fun login(loginDto: LoginDto): TokenDto {
        if(!memberRepository.existsByEmail(loginDto.email)){
            throw IllegalStateException("존재하지 않는 사용자입니다!")
        }

        val authenticationToken = UsernamePasswordAuthenticationToken(
            loginDto.email,loginDto.password
        )

        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        // 해당 객체를 SecurityContextHolder에 저장하고

        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider.createToken(authentication)
        val authorities = authentication.authorities
        return TokenDto(
            token = jwt,
            authority =  authorities.toString()
        )
    }
}