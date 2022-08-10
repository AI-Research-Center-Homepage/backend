package byuntil.backend.member.controller.common;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.common.exception.TypeNotExistException;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.dto.request.*;
import byuntil.backend.member.dto.request.save.*;
import byuntil.backend.member.dto.response.MemberLookupDto;
import byuntil.backend.member.dto.response.MembersLookupDto;
import byuntil.backend.member.dto.response.one.*;
import byuntil.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        if(position.equals("committee")){
            List<OneCommitteeResponseDto> members = (List<OneCommitteeResponseDto>)memberService.findAllByPosition(position);
            List<MemberLookupDto> memberLookupDtoList = new ArrayList<>();
            for (OneCommitteeResponseDto dto: members) {
                memberLookupDtoList.add(MemberLookupDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).major(dto.getMajor()).build());
            }
            MembersLookupDto membersLookupDto = MembersLookupDto.builder().members(memberLookupDtoList).build();
            return ResponseEntity.ok().body(membersLookupDto);
        }
        else if(position.equals("graduate")){
            List<OneGraduateResponseDto> members = (List<OneGraduateResponseDto>)memberService.findAllByPosition(position);
            List<MemberLookupDto> memberLookupDtoList = new ArrayList<>();
            for (OneGraduateResponseDto dto: members) {
                memberLookupDtoList.add(MemberLookupDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).major(dto.getMajor()).build());
            }
            MembersLookupDto membersLookupDto = MembersLookupDto.builder().members(memberLookupDtoList).build();
            return ResponseEntity.ok().body(membersLookupDto);
        }
        else if(position.equals("professor")){
            List<OneProfessorResponseDto> members = (List<OneProfessorResponseDto>)memberService.findAllByPosition(position);
            List<MemberLookupDto> memberLookupDtoList = new ArrayList<>();
            for (OneProfessorResponseDto dto: members) {
                memberLookupDtoList.add(MemberLookupDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).major(dto.getMajor()).build());
            }
            MembersLookupDto membersLookupDto = MembersLookupDto.builder().members(memberLookupDtoList).build();
            return ResponseEntity.ok().body(membersLookupDto);
        }
        else if(position.equals("researcher")){
            List<OneResearcherResponseDto> members = (List<OneResearcherResponseDto>)memberService.findAllByPosition(position);
            List<MemberLookupDto> memberLookupDtoList = new ArrayList<>();
            for (OneResearcherResponseDto dto: members) {
                memberLookupDtoList.add(MemberLookupDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).major(dto.getMajor()).build());
            }
            MembersLookupDto membersLookupDto = MembersLookupDto.builder().members(memberLookupDtoList).build();
            return ResponseEntity.ok().body(membersLookupDto);
        }
        else if(position.equals("undergraduate")){
            List<OneUndergraduateResponseDto> members = (List<OneUndergraduateResponseDto>)memberService.findAllByPosition(position);
            List<MemberLookupDto> memberLookupDtoList = new ArrayList<>();
            for (OneUndergraduateResponseDto dto: members) {
                memberLookupDtoList.add(MemberLookupDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).major(dto.getMajor()).build());
            }
            MembersLookupDto membersLookupDto = MembersLookupDto.builder().members(memberLookupDtoList).build();
            return ResponseEntity.ok().body(membersLookupDto);
        }
        else{
            throw new TypeNotExistException();
        }

    }

    @PostMapping("/members/committee/new")
    public ResponseEntity join(@RequestBody CommitteeRequestDto memberDto) {
        LoginDto loginDto = createAuth(memberDto.getLoginDto());

        CommitteeSaveDto dto = CommitteeSaveDto.builder().loginDto(loginDto).email(memberDto.getEmail())
                .location(memberDto.getLocation()).image(memberDto.getImage()).position(memberDto.getPosition())
                .major(memberDto.getMajor()).build();
        memberService.saveMember(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
    @PostMapping("/members/graduate/new")
    public ResponseEntity join(@RequestBody GraduateRequestDto memberDto) {
        LoginDto loginDto = createAuth(memberDto.getLoginDto());

        GraduateSaveDto dto = GraduateSaveDto.builder().loginDto(loginDto).email(memberDto.getEmail())
                .admission(memberDto.getAdmission()).major(memberDto.getMajor()).location(memberDto.getLocation())
                .name(memberDto.getName()).image(memberDto.getImage()).build();
        memberService.saveMember(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
    @PostMapping("/members/professor/new")
    public ResponseEntity join(@RequestBody ProfessorRequestDto memberDto) {
        LoginDto loginDto = createAuth(memberDto.getLoginDto());

        ProfessorSaveDto dto = ProfessorSaveDto.builder().loginDto(loginDto).email(memberDto.getEmail())
                .name(memberDto.getName())
                .image(memberDto.getImage()).location(memberDto.getLocation()).major(memberDto.getMajor()).doctorate(memberDto.getDoctorate())
                .number(memberDto.getNumber()).name(memberDto.getName()).build();
        memberService.saveMember(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
    @PostMapping("/members/undergraduate/new")
    public ResponseEntity join(@RequestBody UndergraduateRequestDto memberDto) {
        LoginDto loginDto = createAuth(memberDto.getLoginDto());

        UndergraduateSaveDto dto = UndergraduateSaveDto.builder().loginDto(loginDto).admission(memberDto.getAdmission())
                .image(memberDto.getImage()).location(memberDto.getLocation()).image(memberDto.getImage()).email(memberDto.getEmail())
                .major(memberDto.getMajor()).name(memberDto.getName()).build();
        memberService.saveMember(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }


    @PostMapping("/members/researcher/new")
    public ResponseEntity join(@RequestBody ResearcherRequestDto memberDto) {
        LoginDto loginDto = createAuth(memberDto.getLoginDto());

        ResearcherSaveDto dto = ResearcherSaveDto.builder().loginDto(loginDto).location(memberDto.getLocation())
                .email(memberDto.getEmail()).major(memberDto.getMajor()).image(memberDto.getImage()).name(memberDto.getName())
                .image(memberDto.getImage()).build();
        memberService.saveMember(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");

    }

    //멤버조회 /members/{memberId}
    @GetMapping("/members")
    public ResponseEntity readMember(@RequestParam Long id) {
        Member member = (Member) memberService.findOneMember(id).get();
        String dType = member.getDtype();
        if (dType.equals("Committee")){
            OneCommitteeResponseDto dto = memberService.findOneCommittee(id).toDto();
            return ResponseEntity.ok().body(dto);
        }
        else if(dType.equals("Graduate")){
            OneGraduateResponseDto dto = memberService.findOneGraduate(id).toDto();
            return ResponseEntity.ok().body(dto);

        }
        else if(dType.equals("Professor")){
            OneProfessorResponseDto dto = memberService.findOneProfessor(id).toDto();
            return ResponseEntity.ok().body(dto);
        }
        else if(dType.equals("Researcher")){
            OneResearcherResponseDto dto = memberService.findOneResearcher(id).toDto();
            return ResponseEntity.ok().body(dto);

        }
        else if(dType.equals("Undergraduate")){
            OneUndergraduateResponseDto dto = memberService.findOneUndergraduate(id).toDto();
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
