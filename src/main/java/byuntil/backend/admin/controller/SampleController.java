package byuntil.backend.admin.controller;

import byuntil.backend.admin.controller.domain.dto.LoginDto;
import byuntil.backend.member.dto.request.save.ProfessorSaveDto;
import byuntil.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sample/")
public class SampleController {
    private final MemberService memberService;
/*

    @GetMapping("/all")
    public String exAll(){
        //이 page로 들어와서 member를 생성한다음에 sample/member로 들어가서 로그인하면 로그인 성공
        Collection<SimpleGrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        LoginDto loginDto = new LoginDto("user1", "111", auth, false);
        ProfessorSaveDto dto = ProfessorSaveDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("홍홍홍")
                .major("asdfasdfsa")
                .doctorate("A")
                .loginDto(loginDto)
                .number("01096574723")
                .build();

        memberService.saveMember(dto);

        return "akk";
    }
    @GetMapping("/member")
    public String exMember(@AuthenticationPrincipal LoginDto user){
        log.info(user.getUsername() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return "member";
    }
    @GetMapping("/admin")
    public String exAdmin(){
        log.info("exAdmin");
        return "exadmin";
    }*/
}
