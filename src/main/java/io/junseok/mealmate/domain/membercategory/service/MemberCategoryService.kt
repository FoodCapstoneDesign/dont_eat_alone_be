package io.junseok.mealmate.domain.membercategory.service

import io.junseok.mealmate.domain.member.entity.Member
import io.junseok.mealmate.domain.membercategory.dto.request.CategoryRegister
import io.junseok.mealmate.domain.membercategory.dto.request.CategoryRegisters
import io.junseok.mealmate.domain.membercategory.entity.MemberCategory
import io.junseok.mealmate.domain.membercategory.repository.MemberCategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberCategoryService(
    private val memberCategoryRepository: MemberCategoryRepository
) {
    @Transactional
    fun saveCategory(categoryRegisters: CategoryRegisters, member: Member) {
        val memberCategories: MutableList<MemberCategory> = ArrayList()
        categoryRegisters.categoryRegisters.map {
            MemberCategory(
                categoryName = it.categoryName,
                member = member
            ).also { memberCategories.add(it) }
        }.also { memberCategoryRepository.saveAll(memberCategories) }
    }

    @Transactional
    fun updateMemberCategory(categoryRegisters: List<CategoryRegister>, member: Member) {
        val memberCategoryList: List<MemberCategory> =
            memberCategoryRepository.findAllByMember(member)

        for (i in categoryRegisters.indices) {
            val categoryRegister = categoryRegisters[i]

            if (memberCategoryList.size > i) {
                memberCategoryList[i].modifyCategoryName(categoryRegister.categoryName)
            } else {
                MemberCategory(
                    member=member,
                    categoryName=categoryRegisters[i].categoryName
                ).also { memberCategoryRepository.save(it) }
            }
        }

        if (memberCategoryList.size > categoryRegisters.size) {
            val memberCategories = memberCategoryList.subList(
                categoryRegisters.size, memberCategoryList.size
            )
            memberCategoryRepository.deleteAll(memberCategories)
        }
    }
}
