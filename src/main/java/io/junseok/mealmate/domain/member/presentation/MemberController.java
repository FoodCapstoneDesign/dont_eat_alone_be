package io.junseok.mealmate.domain.member.presentation;


import io.junseok.mealmate.domain.member.dto.request.SignUpDto;
import io.junseok.mealmate.domain.member.dto.response.MemberInfoDto;
import io.junseok.mealmate.domain.member.service.MemberService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Long> singUp(@RequestBody SignUpDto signUpDto){
        return ResponseEntity.ok(memberService.createMember(signUpDto));
    }

    @GetMapping
    public ResponseEntity<MemberInfoDto> getMemberInfo(Principal principal){
        return ResponseEntity.ok(memberService.showMemberInfo(principal.getName()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(Principal principal){
        memberService.resignMember(principal.getName());
        return ResponseEntity.ok().build();
    }
}
