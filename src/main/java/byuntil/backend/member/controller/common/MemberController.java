package byuntil.backend.member.controller.common;

import byuntil.backend.member.domain.entity.member.*;
import byuntil.backend.member.dto.response.*;
import byuntil.backend.member.dto.response.general.*;
import byuntil.backend.member.dto.response.general.list.*;
import byuntil.backend.member.dto.response.one.*;
import byuntil.backend.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/professor")
    public ResponseEntity readProfessors() {
        List<ProfessorAdminResponseDto> adminDtoList = (List<ProfessorAdminResponseDto>) memberService.findAllByPosition(MemberDtype.Professor.toString());
        List<ProfessorResponseDto> response = new ArrayList<>();
        for (ProfessorAdminResponseDto dto: adminDtoList) {
            response.add(ProfessorResponseDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).doctorate(dto.getDoctorate()).number(dto.getNumber())
                    .major(dto.getMajor()).image(dto.getImage()).location(dto.getLocation()).build());
        }
        return ResponseEntity.ok().body(ProfessorResponseListDto.builder().professor(response).build());
    }

    @GetMapping("/graduate")
    public ResponseEntity readGraduates() {
        List<GraduateAdminResponseDto> adminDtoList = (List<GraduateAdminResponseDto>) memberService.findAllByPosition(MemberDtype.Graduate.toString());
        List<GraduateResponseDto> response = new ArrayList<>();
        for (GraduateAdminResponseDto dto: adminDtoList) {
            response.add(GraduateResponseDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).admission(dto.getAdmission()).major(dto.getMajor())
                    .image(dto.getImage()).location(dto.getLocation()).build());

        }
        return ResponseEntity.ok().body(GraduateResponseListDto.builder().graduate(response).build());
    }

    @GetMapping("/undergraduate")
    public ResponseEntity readUnderGraduates() {
        List<UndergraduateAdminResponseDto> adminDtoList = (List<UndergraduateAdminResponseDto>) memberService.findAllByPosition(MemberDtype.Undergraduate.toString());
        List<UndergraduateReponseDto> response = new ArrayList<>();
        for (UndergraduateAdminResponseDto dto: adminDtoList) {
            response.add(UndergraduateReponseDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).admission(dto.getAdmission())
                    .image(dto.getImage()).location(dto.getLocation()).major(dto.getMajor()).build());
        }
        return ResponseEntity.ok().body(UndergraduateResponseListDto.builder().undergraduate(response).build());
    }

    @GetMapping("/committee")
    public ResponseEntity readCommittee() {
        List<CommitteeAdminResponseDto> adminDtoList = (List<CommitteeAdminResponseDto>) memberService.findAllByPosition(MemberDtype.Committee.toString());
        List<CommitteeResponseDto> response = new ArrayList<>();
        for (CommitteeAdminResponseDto dto: adminDtoList) {
            response.add(CommitteeResponseDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).image(dto.getImage()).location(dto.getLocation())
                    .position(dto.getPosition()).major(dto.getMajor()).build());
        }
        return ResponseEntity.ok().body(CommitteeResponseListDto.builder().committee(response).build());
    }

    @GetMapping("/researcher")
    public ResponseEntity readResearcher() {
        List<ResearcherAdminResponseDto> adminDtoList  = (List<ResearcherAdminResponseDto>) memberService.findAllByPosition(MemberDtype.Researcher.toString());
        List<ResearcherResponseDto> response = new ArrayList<>();
        for (ResearcherAdminResponseDto dto: adminDtoList) {
            response.add(ResearcherResponseDto.builder().name(dto.getName()).id(dto.getId()).email(dto.getEmail()).image(dto.getImage()).location(dto.getLocation())
                    .major(dto.getMajor()).build());
        }
        return ResponseEntity.ok().body(ResearcherResponseListDto.builder().researcher(response).build());
    }
}
