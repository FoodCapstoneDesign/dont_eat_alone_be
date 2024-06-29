package io.junseok.mealmate.auth.service

import io.junseok.mealmate.domain.member.entity.Member
import io.junseok.mealmate.domain.member.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component("userDetailsService")
class CustomUserDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService{
    override fun loadUserByUsername(email: String): UserDetails {
        val member = memberRepository.findOneWithAuthoritiesByEmail(email)
        return createAdmin(email,member!!)
    }

    fun createAdmin(email: String, member: Member): User{
        if(!member.activated){
            throw RuntimeException(email+"가 활성화되어 있지 않습니다")
        }

        val grantedAuthorities = SimpleGrantedAuthority(member.authority.toString())
        return User(member.email, member.password, setOf(grantedAuthorities))
    }
}