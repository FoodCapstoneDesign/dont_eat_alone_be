package io.junseok.mealmate.domain.member.dto.response;


data class MemberInfoDto(
    val email: String,
    val password: String,
    val nickname: String,
    val studentNumber: String,
    val school: String,
    val department: String,
)
