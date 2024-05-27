package io.junseok.mealmate.domain.member.dto.request;

import io.junseok.mealmate.domain.membercategory.dto.request.CategoryRegister;
import java.util.List;

public record ModifyMemberInfo(
    String password,
    List<CategoryRegister> categoryRegisters
) {

}
