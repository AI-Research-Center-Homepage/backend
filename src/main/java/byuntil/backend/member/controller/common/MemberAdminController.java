package byuntil.backend.member.controller.common;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.common.exception.TypeNotExistException;
import byuntil.backend.member.domain.entity.member.Committee;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.entity.member.Researcher;
import byuntil.backend.member.dto.request.*;
import byuntil.backend.member.dto.response.MemberLookupDto;
import byuntil.backend.member.dto.response.MembersLookupDto;
import byuntil.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class MemberAdminController {
    private final MemberService memberService;

    @PutMapping("/members/{memberId}")
    public ResponseEntity updateMember(@PathVariable Long memberId, @RequestBody MemberAllInfoDto memberDto) throws Throwable {
        memberService.updateMember(memberId, memberDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
    }


    @GetMapping("/members/{position}")
    public ResponseEntity readMembersByPosition(@PathVariable String position){
        List<MemberSaveRequestDto> members = (List<MemberSaveRequestDto>) memberService.findAllByPosition(position);
        List<MemberLookupDto> memberLookupDtoList = new ArrayList<>();
        for (MemberSaveRequestDto dto: members) {
            memberLookupDtoList.add(MemberLookupDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).major(dto.getMajor()).build());
        }
        MembersLookupDto membersLookupDto = MembersLookupDto.builder().members(memberLookupDtoList).build();
        return ResponseEntity.ok().body(membersLookupDto);
    }


    //멤버등록 /members/new?position={position}
    @PostMapping("/members/new")
    public ResponseEntity join(@RequestParam String position, @RequestBody MemberAllInfoDto memberDto) {
        //position이 뭐냐에 따라서..
        //일단 모든 정보를 담을 수 있는.. dto을 정의하고.. positon이 뭐냐에 따라서
        //committeesaveReqeustDto로할지.. 이런것들을 if문으로 나눈다음에?
        //멤버를 저장한다..

        LoginDto loginDto = createAuth(memberDto.getLoginDto());

        if(position.equals("Committee")){
            CommitteeSaveRequestDto dto = CommitteeSaveRequestDto.builder().loginDto(loginDto).email(memberDto.getEmail())
                    .location(memberDto.getLocation()).image(memberDto.getImage()).position(memberDto.getPosition())
                    .major(memberDto.getMajor()).build();
            memberService.saveMember(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        }
        else if(position.equals("Graduate")){
            GraduateSaveRequestDto dto = GraduateSaveRequestDto.builder().loginDto(loginDto).email(memberDto.getEmail())
                    .admission(memberDto.getAdmission()).major(memberDto.getMajor()).location(memberDto.getLocation())
                    .name(memberDto.getName()).image(memberDto.getImage()).build();
            memberService.saveMember(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        }
        else if(position.equals("Professor")){
            ProfessorSaveRequestDto dto = ProfessorSaveRequestDto.builder().loginDto(loginDto).email(memberDto.getEmail())
                    .name(memberDto.getName())
                    .image(memberDto.getImage()).location(memberDto.getLocation()).major(memberDto.getMajor()).doctorate(memberDto.getDoctorate())
                    .number(memberDto.getNumber()).name(memberDto.getName()).build();
            memberService.saveMember(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        }
        else if(position.equals("Researcher")){
            ResearcherSaveRequestDto dto = ResearcherSaveRequestDto.builder().loginDto(loginDto).location(memberDto.getLocation())
                    .email(memberDto.getEmail()).major(memberDto.getMajor()).image(memberDto.getImage()).name(memberDto.getName())
                    .image(memberDto.getImage()).build();
            memberService.saveMember(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        }
        else if(position.equals("Undergraduate")){
            UndergraduateSaveRequestDto dto = UndergraduateSaveRequestDto.builder().loginDto(loginDto).admission(memberDto.getAdmission())
                    .image(memberDto.getImage()).location(memberDto.getLocation()).image(memberDto.getImage()).email(memberDto.getEmail())
                    .major(memberDto.getMajor()).name(memberDto.getName()).build();
            memberService.saveMember(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        }
        else{
            //에러
            throw new TypeNotExistException();
        }
    }

    //멤버조회 /members/{memberId}
    @GetMapping("/members")
    public ResponseEntity readMember(@RequestParam Long id) {
        Member member = (Member) memberService.findOneMember(id).get();
        String dType = member.getDtype();
        if (dType.equals("Committee")){
            CommitteeSaveRequestDto dto = memberService.findOneCommittee(id).toDto();
            return ResponseEntity.ok().body(dto);
        }
        else if(dType.equals("Graduate")){
            GraduateSaveRequestDto dto = memberService.findOneGraduate(id).toDto();
            return ResponseEntity.ok().body(dto);

        }
        else if(dType.equals("Professor")){
            ProfessorSaveRequestDto dto = memberService.findOneProfessor(id).toDto();
            return ResponseEntity.ok().body(dto);
        }
        else if(dType.equals("Researcher")){
            ResearcherSaveRequestDto dto = memberService.findOneResearcher(id).toDto();
            return ResponseEntity.ok().body(dto);

        }
        else if(dType.equals("Undergraduate")){
            UndergraduateSaveRequestDto dto = memberService.findOneUndergraduate(id).toDto();
            return ResponseEntity.ok().body(dto);
        }
        else{
            throw new TypeNotExistException();
        }

    }

    //멤버 탈퇴
    @DeleteMapping("/members")
    public ResponseEntity resignMember(@RequestParam Long id) throws Throwable {
        memberService.secession(id);
        return ResponseEntity.ok().build();
    }

    public LoginDto createAuth(LoginDto loginDto){
        Collection<SimpleGrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new LoginDto(loginDto.getLoginId(), loginDto.getLoginPw(), auth, false);
    }


}
