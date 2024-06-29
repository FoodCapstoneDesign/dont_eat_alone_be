package io.junseok.mealmate.domain.member.dto.request;

import io.junseok.mealmate.domain.membercategory.dto.request.CategoryRegister
import io.junseok.mealmate.domain.membercategory.dto.request.CategoryRegisters

data class SignUpDto(
    val email: String,
    val password: String,
    val categoryRegisters: List<MemberCategoryRequest>
)
data class MemberCategoryRequest(val categoryName: String)


fun List<MemberCategoryRequest>.toCategoryRegisters() = CategoryRegisters(
    this.map {
        CategoryRegister(
            categoryName = it.categoryName
        )
    }
)
