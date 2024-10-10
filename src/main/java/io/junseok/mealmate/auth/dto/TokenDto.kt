package io.junseok.mealmate.auth.dto

import io.junseok.mealmate.domain.member.entity.Authority

data class TokenDto(
    val token: String,
    val authority: String
)
