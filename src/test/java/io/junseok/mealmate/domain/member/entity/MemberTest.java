package io.junseok.mealmate.domain.member.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName(value = "Member Domain Test")
class MemberTest {

    @DisplayName("멤버_객체_생성")
    @Test
    void 멤버_객체_생성() {
        //given
        Member member = Member.builder()
            .email("wnstjr1204@naver.com")
            .build();
        //then
        assertThat(member.getEmail()).isEqualTo("wnstjr1204@naver.com");
    }
}