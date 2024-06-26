package io.junseok.mealmate.domain.member.dto.request

import io.junseok.mealmate.domain.membercategory.dto.request.CategoryRegister

data class ModifyMemberInfo(
    val password: String,
    val categoryRegisters: List<CategoryRegister>
)
