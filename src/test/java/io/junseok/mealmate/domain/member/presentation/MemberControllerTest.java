package io.junseok.mealmate.domain.member.presentation;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.junseok.mealmate.domain.member.dto.request.EmailCheck;
import io.junseok.mealmate.domain.member.dto.request.ModifyMemberInfo;
import io.junseok.mealmate.domain.member.dto.request.SignUpDto;
import io.junseok.mealmate.domain.member.dto.response.MemberInfoDto;
import io.junseok.mealmate.domain.member.service.MemberService;
import io.junseok.mealmate.domain.membercategory.dto.request.CategoryRegister;
import io.junseok.mealmate.exception.ErrorCode;
import io.junseok.mealmate.exception.MealMateException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(MemberController.class)
@ExtendWith(SpringExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(springSecurity())
            .defaultRequest(post("/**").with(csrf()))
            .defaultRequest(get("/**").with(csrf()))
            .defaultRequest(delete("/**").with(csrf()))
            .defaultRequest(patch("/**").with(csrf()))
            .build();
    }

    @Test
    @DisplayName("회원가입_API_테스트")
    @WithMockUser
    void 회원가입_Api() throws Exception {
        //given

        CategoryRegister categoryRegister = new CategoryRegister("test");
        List<CategoryRegister> list = List.of(categoryRegister);
        SignUpDto signUpDto = new SignUpDto("user@example.com", "password123", list);
        Long expectedUserId = 1L;

        //when
        when(memberService.createMember(signUpDto)).thenReturn(expectedUserId);
        //then
        mockMvc.perform(post("/api/member/signup")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDto)))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedUserId.toString()));
    }

    @Test
    @DisplayName("멤버_정보_API")
    @WithMockUser
    void 멤버_정보_Api() throws Exception {
        //given
        MemberInfoDto memberInfoDto = new MemberInfoDto(
            "wnstjr1204@naver.com",
            "1234"
        );
        String token = "Bearer token";

        String actualDto = objectMapper.writeValueAsString(memberInfoDto);
        //when
        when(memberService.showMemberInfo("user")).thenReturn(memberInfoDto);
        mockMvc.perform(
                get("/api/member")
                    .header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(actualDto));
    }

    @Test
    @DisplayName("멤버_회원탈퇴_API")
    @WithMockUser(username = "wnstjr1201@naver.com")
    void 멤버_회원탈퇴_Api() throws Exception {
        //when
        doNothing().when(memberService).resignMember("wnstjr1201@naver.com");
        //then
        mockMvc.perform(delete("/api/member")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("이메일_유효성검사_API")
    @WithMockUser
    void 이메일_유효성검사_Api() throws Exception {
        //given
        EmailCheck emailCheck = new EmailCheck("wnstjr@naver.com");
        EmailCheck emailCheck1 = new EmailCheck("wnstjrnaver.com");
        //when
        //유효한 이메일
        doNothing().when(memberService).validEmail(emailCheck1.email());

        //유효하지 않은 이메일인 경우
        doThrow(new MealMateException(ErrorCode.INVALID_EMAIL_FORMAT))
            .when(memberService).validEmail(emailCheck1.email());
        //then
        mockMvc.perform(post("/api/member/check-email")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(emailCheck))
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("멤버_회원정보_수정_API")
    @WithMockUser(username = "test@naver.com")
    void 멤버_회원정보_수정_Api() throws Exception {
        //given
        CategoryRegister categoryRegister = new CategoryRegister("test");
        List<CategoryRegister> list = List.of(categoryRegister);

        ModifyMemberInfo modifyMemberInfo = new ModifyMemberInfo(
            "1234",
            list
        );

        //when
        doNothing().when(memberService).update(modifyMemberInfo, "test@naver.com");
        //then
        mockMvc.perform(patch("/api/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifyMemberInfo))
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}