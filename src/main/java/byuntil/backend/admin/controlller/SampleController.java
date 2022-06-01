package byuntil.backend.admin.controlller;

import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.member.domain.repository.MemberRepository;
import byuntil.backend.member.dto.request.ProfessorSaveRequestDto;
import byuntil.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
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


    @GetMapping("/all")
    public String exAll(){
        //이 page로 들어와서 member를 생성한다음에 sample/member로 들어가서 로그인하면 로그인 성공
        Collection<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        AdminDto adminDto = new AdminDto("user1", "111", auth, false);
        ProfessorSaveRequestDto dto = ProfessorSaveRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("홍홍홍")
                .major("asdfasdfsa")
                .doctorate("A")
                .location("전주")
                .number("01096574723")
                .adminDto(adminDto)
                .build();
        memberService.saveMember(dto);

        return "akk";
    }
    @GetMapping("/member")
    public String exMember(@AuthenticationPrincipal AdminDto user){
        log.info(user.getUsername() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return "member";
    }
    @GetMapping("/admin")
    public String exAdmin(){
        log.info("exAdmin");
        return "exadmin";
    }
}
