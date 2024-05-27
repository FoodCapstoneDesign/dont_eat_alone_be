package io.junseok.mealmate.domain.member.dto.response;

import io.junseok.mealmate.domain.membercategory.dto.request.CategoryRegister;
import java.util.List;
import lombok.Builder;

@Builder
public record MemberInfoDto(
    String email,
    String password
) {

}
