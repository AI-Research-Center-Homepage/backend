package byuntil.backend.member.controller.common;

import byuntil.backend.common.exception.TypeNotExistException;
import byuntil.backend.member.domain.entity.member.Committee;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.entity.member.Researcher;
import byuntil.backend.member.dto.request.*;
import byuntil.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/")
public class MemberAdminController {
    private final MemberService memberService;
    //멤버등록 /members/new?position={position}
    @PostMapping("/members/new")
    public ResponseEntity<?> join(@RequestParam String position, @ModelAttribute MemberAllInfoDto memberDto) {
        //position이 뭐냐에 따라서..
        //일단 모든 정보를 담을 수 있는.. dto을 정의하고.. positon이 뭐냐에 따라서
        //committeesaveReqeustDto로할지.. 이런것들을 if문으로 나눈다음에?
        //멤버를 저장한다..
        if(position.equals("Committee")){
            CommitteeSaveRequestDto dto = CommitteeSaveRequestDto.builder().loginDto(memberDto.getLoginDto()).email(memberDto.getEmail())
                    .location(memberDto.getLocation()).image(memberDto.getImage()).position(memberDto.getPosition())
                    .major(memberDto.getMajor()).build();
            memberService.saveMember(dto);
            return ResponseEntity.ok().body(dto);
        }
        else if(position.equals("Graduate")){
            GraduateSaveRequestDto dto = GraduateSaveRequestDto.builder().loginDto(memberDto.getLoginDto()).email(memberDto.getEmail())
                    .admission(memberDto.getAdmission()).major(memberDto.getMajor()).location(memberDto.getLocation())
                    .name(memberDto.getName()).image(memberDto.getImage()).build();
            memberService.saveMember(dto);
            return ResponseEntity.ok().body(dto);
        }
        else if(position.equals("Professor")){
            ProfessorSaveRequestDto dto = ProfessorSaveRequestDto.builder().loginDto(memberDto.getLoginDto()).email(memberDto.getEmail())
                    .image(memberDto.getImage()).location(memberDto.getLocation()).major(memberDto.getMajor()).doctorate(memberDto.getDoctorate())
                    .number(memberDto.getNumber()).name(memberDto.getName()).build();
            memberService.saveMember(dto);
            return ResponseEntity.ok().body(dto);
        }
        else if(position.equals("Researcher")){
            ResearcherSaveRequestDto dto = ResearcherSaveRequestDto.builder().loginDto(memberDto.getLoginDto()).location(memberDto.getLocation())
                    .email(memberDto.getEmail()).major(memberDto.getMajor()).image(memberDto.getImage()).name(memberDto.getName())
                    .image(memberDto.getImage()).build();
            memberService.saveMember(dto);
            return ResponseEntity.ok().body(dto);
        }
        else if(position.equals("Undergraduate")){
            UndergraduateSaveRequestDto dto = UndergraduateSaveRequestDto.builder().loginDto(memberDto.getLoginDto()).admission(memberDto.getAdmission())
                    .image(memberDto.getImage()).location(memberDto.getLocation()).image(memberDto.getImage()).email(memberDto.getEmail())
                    .major(memberDto.getMajor()).name(memberDto.getName()).build();
            memberService.saveMember(dto);
            return ResponseEntity.ok().body(dto);
        }
        else{
            //에러
            throw new TypeNotExistException();
        }
    }
    //멤버조회 /members/{memberId}
    @GetMapping("/members")
    public ResponseEntity<?> readMember(@RequestParam Long id) {
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
    public ResponseEntity<Void> resignMember(@RequestParam Long id) throws Throwable {
        memberService.secession(id);
        return ResponseEntity.ok().build();
    }


}
