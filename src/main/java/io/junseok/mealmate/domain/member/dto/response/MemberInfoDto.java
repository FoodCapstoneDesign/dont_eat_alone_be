package io.junseok.mealmate.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberInfoDto(String email, String password) {

}
