package io.junseok.mealmate.domain.membercategory.service;

import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.membercategory.dto.request.CategoryRegister;
import io.junseok.mealmate.domain.membercategory.entity.MemberCategory;
import io.junseok.mealmate.domain.membercategory.repository.MemberCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCategoryService {

    private final MemberCategoryRepository memberCategoryRepository;

    @Transactional
    public void saveCategory(List<CategoryRegister> categoryRegisters, Member member) {
        List<MemberCategory> memberCategories = new ArrayList<>();
        for(CategoryRegister categoryRegister : categoryRegisters){
            MemberCategory category = MemberCategory.builder()
                .categoryName(categoryRegister.categoryName())
                .member(member)
                .build();
            memberCategories.add(category);
        }
        memberCategoryRepository.saveAll(memberCategories);
    }
}
