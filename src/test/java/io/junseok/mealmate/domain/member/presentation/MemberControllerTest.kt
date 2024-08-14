/*
package io.junseok.mealmate.domain.member.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import io.junseok.mealmate.domain.member.dto.request.EmailCheck
import io.junseok.mealmate.domain.member.dto.request.MemberCategoryRequest
import io.junseok.mealmate.domain.member.dto.request.ModifyMemberInfo
import io.junseok.mealmate.domain.member.dto.request.SignUpDto
import io.junseok.mealmate.domain.member.dto.response.MemberInfoDto
import io.junseok.mealmate.domain.member.service.MemberService
import io.junseok.mealmate.domain.membercategory.dto.request.CategoryRegister
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.apache.http.client.methods.RequestBuilder.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(MemberController::class)
@ExtendWith(MockKExtension::class)
@MockBean(
    JpaMetamodelMappingContext::class
)
@AutoConfigureMockMvc(addFilters = false)
internal class MemberControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var memberService: MemberService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        memberService = mockk()
        val userDetails: UserDetails =
            User.withUsername("user").password("password").roles("USER").build()
        val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        SecurityContextHolder.getContext().authentication = auth
    }

    @Test
    @DisplayName("회원가입_API_테스트")
    @WithMockUser
    @Throws(
        Exception::class
    )
    fun 회원가입_Api() {
        //given
        val list = listOf(MemberCategoryRequest(categoryName = "하이"))
        val signUpDto = SignUpDto("user@example.com", "password123", list)
        val expectedUserId = 1L

        //when
        Mockito.`when`(memberService.createMember(signUpDto)).thenReturn(expectedUserId)
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/member/signup")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDto)) //직렬화
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(expectedUserId.toString()))
    }

    @Test
    @DisplayName("멤버_정보_API")
    @WithMockUser(username = "user")
    @Throws(Exception::class)
    fun 멤버_정보_Api() {
        // given
        val memberInfoDto = MemberInfoDto(
            "wnstjr1204@naver.com",
            "1234"
        )
        val token = "Bearer token"

        val actualDto = objectMapper.writeValueAsString(memberInfoDto)

        // when
        every { memberService.showMemberInfo("user") } returns memberInfoDto
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/member")
                .header("Authorization", token)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user("user").roles("USER"))
                .principal {
                    UsernamePasswordAuthenticationToken(
                        "user",
                        "password"
                    ).toString()
                }  // Principal 설정
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect { MockMvcResultMatchers.status().isOk() }
            .andExpect { MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON) }
            .andExpect { MockMvcResultMatchers.content().json(actualDto) }
    }

    @Test
    @DisplayName("멤버_회원탈퇴_API")
    @WithMockUser(username = "wnstjr1201@naver.com")
    @Throws(Exception::class)
    fun 멤버_회원탈퇴_Api() {

        every { memberService.resignMember(any()) } returns Unit
        val token = "Bearer token"
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/member")
                .header("Authorization", token)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user("user").roles("USER"))
                .principal {
                    UsernamePasswordAuthenticationToken(
                        "user",
                        "password"
                    ).toString()
                }  // Principal 설정
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
    }

    @Test
    @DisplayName("이메일_유효성검사_API")
    @WithMockUser
    @Throws(
        Exception::class
    )
    fun 이메일_유효성검사_Api() {
        //given
        val emailCheck = EmailCheck("wnstjr@naver.com")

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/member/check-email")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Gson().toJson(emailCheck))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @DisplayName("멤버_회원정보_수정_API")
    @WithMockUser(username = "test@naver.com")
    @Throws(
        Exception::class
    )
    fun 멤버_회원정보_수정_Api() {
        //given
        val categoryRegister = CategoryRegister("test")
        val list = listOf(categoryRegister)

        val modifyMemberInfo = ModifyMemberInfo(
            "1234",
            list
        )

        every { memberService.update(modifyMemberInfo,any()) } returns Unit

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/member")
                .header("Authorization", "token")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user("user").roles("USER"))
                .principal {
                    UsernamePasswordAuthenticationToken(
                        "user",
                        "password"
                    ).toString()
                }  // Principal 설정
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
    }
}
*/
