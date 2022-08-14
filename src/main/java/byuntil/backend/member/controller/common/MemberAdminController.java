package byuntil.backend.member.controller.common;

import byuntil.backend.admin.controller.domain.dto.LoginDto;
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
            List<CommitteeAdminResponseDto> members = (List<CommitteeAdminResponseDto>)memberService.findAllByPosition(position);
            List<MemberLookupDto> memberLookupDtoList = new ArrayList<>();
            for (CommitteeAdminResponseDto dto: members) {
                memberLookupDtoList.add(MemberLookupDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).major(dto.getMajor()).build());
            }
            MembersLookupDto membersLookupDto = MembersLookupDto.builder().members(memberLookupDtoList).build();
            return ResponseEntity.ok().body(membersLookupDto);
        }
        else if(position.equals("graduate")){
            List<GraduateAdminResponseDto> members = (List<GraduateAdminResponseDto>)memberService.findAllByPosition(position);
            List<MemberLookupDto> memberLookupDtoList = new ArrayList<>();
            for (GraduateAdminResponseDto dto: members) {
                memberLookupDtoList.add(MemberLookupDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).major(dto.getMajor()).build());
            }
            MembersLookupDto membersLookupDto = MembersLookupDto.builder().members(memberLookupDtoList).build();
            return ResponseEntity.ok().body(membersLookupDto);
        }
        else if(position.equals("professor")){
            List<ProfessorAdminResponseDto> members = (List<ProfessorAdminResponseDto>)memberService.findAllByPosition(position);
            List<MemberLookupDto> memberLookupDtoList = new ArrayList<>();
            for (ProfessorAdminResponseDto dto: members) {
                memberLookupDtoList.add(MemberLookupDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).major(dto.getMajor()).build());
            }
            MembersLookupDto membersLookupDto = MembersLookupDto.builder().members(memberLookupDtoList).build();
            return ResponseEntity.ok().body(membersLookupDto);
        }
        else if(position.equals("researcher")){
            List<ResearcherAdminResponseDto> members = (List<ResearcherAdminResponseDto>)memberService.findAllByPosition(position);
            List<MemberLookupDto> memberLookupDtoList = new ArrayList<>();
            for (ResearcherAdminResponseDto dto: members) {
                memberLookupDtoList.add(MemberLookupDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).major(dto.getMajor()).build());
            }
            MembersLookupDto membersLookupDto = MembersLookupDto.builder().members(memberLookupDtoList).build();
            return ResponseEntity.ok().body(membersLookupDto);
        }
        else if(position.equals("undergraduate")){
            List<UndergraduateAdminResponseDto> members = (List<UndergraduateAdminResponseDto>)memberService.findAllByPosition(position);
            List<MemberLookupDto> memberLookupDtoList = new ArrayList<>();
            for (UndergraduateAdminResponseDto dto: members) {
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
        if(memberDto.getLoginDto()!=null && !memberDto.getLoginDto().getLoginId().equals("")){
            LoginDto loginDto = createAuth(memberDto.getLoginDto());
            CommitteeSaveDto dto = CommitteeSaveDto.builder().loginDto(loginDto).email(memberDto.getEmail()).name(memberDto.getName())
                    .location(memberDto.getLocation()).image(memberDto.getImage()).position(memberDto.getPosition())
                    .major(memberDto.getMajor()).build();
            memberService.saveMember(dto);
        }
        else{
            CommitteeSaveDto dto = CommitteeSaveDto.builder().email(memberDto.getEmail()).name(memberDto.getName())
                    .location(memberDto.getLocation()).image(memberDto.getImage()).position(memberDto.getPosition())
                    .major(memberDto.getMajor()).build();
            memberService.saveMember(dto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
    @PostMapping("/members/graduate/new")
    public ResponseEntity join(@RequestBody GraduateRequestDto memberDto) {
        if(memberDto.getLoginDto()!=null && !memberDto.getLoginDto().getLoginId().equals("")){
            LoginDto loginDto = createAuth(memberDto.getLoginDto());
            GraduateSaveDto dto = GraduateSaveDto.builder().loginDto(loginDto).email(memberDto.getEmail())
                    .admission(memberDto.getAdmission()).major(memberDto.getMajor()).location(memberDto.getLocation())
                    .name(memberDto.getName()).image(memberDto.getImage()).build();
            memberService.saveMember(dto);
        }
        else{
            GraduateSaveDto dto = GraduateSaveDto.builder().email(memberDto.getEmail())
                    .admission(memberDto.getAdmission()).major(memberDto.getMajor()).location(memberDto.getLocation())
                    .name(memberDto.getName()).image(memberDto.getImage()).build();
            memberService.saveMember(dto);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
    @PostMapping("/members/professor/new")
    public ResponseEntity join(@RequestBody ProfessorRequestDto memberDto) {
        if(memberDto.getLoginDto()!=null && !memberDto.getLoginDto().getLoginId().equals("")){
            LoginDto loginDto = createAuth(memberDto.getLoginDto());

            ProfessorSaveDto dto = ProfessorSaveDto.builder().loginDto(loginDto).email(memberDto.getEmail())
                    .name(memberDto.getName())
                    .image(memberDto.getImage()).location(memberDto.getLocation()).major(memberDto.getMajor()).doctorate(memberDto.getDoctorate())
                    .number(memberDto.getNumber()).name(memberDto.getName()).build();
            memberService.saveMember(dto);
        }
        else{
            ProfessorSaveDto dto = ProfessorSaveDto.builder().email(memberDto.getEmail())
                    .name(memberDto.getName())
                    .image(memberDto.getImage()).location(memberDto.getLocation()).major(memberDto.getMajor()).doctorate(memberDto.getDoctorate())
                    .number(memberDto.getNumber()).name(memberDto.getName()).build();
            memberService.saveMember(dto);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
    @PostMapping("/members/undergraduate/new")
    public ResponseEntity join(@RequestBody UndergraduateRequestDto memberDto) {
        if(memberDto.getLoginDto()!=null && !memberDto.getLoginDto().getLoginId().equals("")){
            LoginDto loginDto = createAuth(memberDto.getLoginDto());

            UndergraduateSaveDto dto = UndergraduateSaveDto.builder().loginDto(loginDto).admission(memberDto.getAdmission())
                    .image(memberDto.getImage()).location(memberDto.getLocation()).image(memberDto.getImage()).email(memberDto.getEmail())
                    .major(memberDto.getMajor()).name(memberDto.getName()).build();
            memberService.saveMember(dto);
        }
        else {
            UndergraduateSaveDto dto = UndergraduateSaveDto.builder().admission(memberDto.getAdmission())
                    .image(memberDto.getImage()).location(memberDto.getLocation()).image(memberDto.getImage()).email(memberDto.getEmail())
                    .major(memberDto.getMajor()).name(memberDto.getName()).build();
            memberService.saveMember(dto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }


    @PostMapping("/members/researcher/new")
    public ResponseEntity join(@RequestBody ResearcherRequestDto memberDto) {
        if(memberDto.getLoginDto()!=null && !memberDto.getLoginDto().getLoginId().equals("")){
            LoginDto loginDto = createAuth(memberDto.getLoginDto());

            ResearcherSaveDto dto = ResearcherSaveDto.builder().loginDto(loginDto).location(memberDto.getLocation())
                    .email(memberDto.getEmail()).major(memberDto.getMajor()).image(memberDto.getImage()).name(memberDto.getName())
                    .image(memberDto.getImage()).build();
            memberService.saveMember(dto);
        }
        else {
            ResearcherSaveDto dto = ResearcherSaveDto.builder().location(memberDto.getLocation())
                    .email(memberDto.getEmail()).major(memberDto.getMajor()).image(memberDto.getImage()).name(memberDto.getName())
                    .image(memberDto.getImage()).build();
            memberService.saveMember(dto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("");

    }

    //멤버조회 /members/{memberId}
    @GetMapping("/members")
    public ResponseEntity readMember(@RequestParam Long id) {
        Member member = (Member) memberService.findOneMember(id).get();
        String dType = member.getDtype();
        if (dType.equals("Committee")){
            CommitteeAdminResponseDto dto = memberService.findOneCommittee(id).toDto();
            return ResponseEntity.ok().body(dto);
        }
        else if(dType.equals("Graduate")){
            GraduateAdminResponseDto dto = memberService.findOneGraduate(id).toDto();
            return ResponseEntity.ok().body(dto);

        }
        else if(dType.equals("Professor")){
            ProfessorAdminResponseDto dto = memberService.findOneProfessor(id).toDto();
            return ResponseEntity.ok().body(dto);
        }
        else if(dType.equals("Researcher")){
            ResearcherAdminResponseDto dto = memberService.findOneResearcher(id).toDto();
            return ResponseEntity.ok().body(dto);

        }
        else if(dType.equals("Undergraduate")){
            UndergraduateAdminResponseDto dto = memberService.findOneUndergraduate(id).toDto();
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
