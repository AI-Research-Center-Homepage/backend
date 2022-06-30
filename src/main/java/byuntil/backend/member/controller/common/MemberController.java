package byuntil.backend.member.controller.common;

import byuntil.backend.member.domain.entity.member.*;
import byuntil.backend.member.dto.response.*;
import byuntil.backend.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/professor")
    public ResponseEntity<?> readProfessors() {
        List<Member> members = memberService.findAllByPosition(MemberDtype.Professor.toString());
        List<Professor> professors = members.stream().map(member -> (Professor) member).toList();
        List<ProfessorDto> professorDtos = professors.stream().map(ProfessorDto::new).toList();
        ProfessorResponseDto<ProfessorDto> response = ProfessorResponseDto.<ProfessorDto>builder().professor(professorDtos).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/graduate")
    public ResponseEntity<?> readGraduates() {
        List<Member> members = memberService.findAllByPosition(MemberDtype.Graduate.toString());
        List<Graduate> graduates = members.stream().map(member -> (Graduate) member).toList();
        List<GraduateDto> graduateDtos = graduates.stream().map(GraduateDto::new).toList();
        GraduateResponseDto<GraduateDto> response = GraduateResponseDto.<GraduateDto>builder().graduate(graduateDtos).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/undergraduate")
    public ResponseEntity<?> readUnderGraduates() {
        List<Member> members = memberService.findAllByPosition(MemberDtype.Undergraduate.toString());
        List<Undergraduate> undergraduates = members.stream().map(member -> (Undergraduate) member).toList();
        List<UndergraduateDto> undergraduateDtos = undergraduates.stream().map(UndergraduateDto::new).toList();
        UndergraduateResponseDto<UndergraduateDto> response = UndergraduateResponseDto.<UndergraduateDto>builder().undergraduate(undergraduateDtos).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/committee")
    public ResponseEntity<?> readCommittee() {
        List<Member> members = memberService.findAllByPosition(MemberDtype.Committee.toString());
        List<Committee> committees = members.stream().map(member -> (Committee) member).toList();
        List<CommitteeDto> committeeDtos = committees.stream().map(CommitteeDto::new).toList();
        CommitteeResponseDto<CommitteeDto> response = CommitteeResponseDto.<CommitteeDto>builder().committee(committeeDtos).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/researcher")
    public ResponseEntity<?> readResearcher() {
        List<Member> members = memberService.findAllByPosition(MemberDtype.Researcher.toString());
        List<Researcher> researchers = members.stream().map(member -> (Researcher) member).toList();
        List<ResearcherDto> researcherDtos = researchers.stream().map(ResearcherDto::new).toList();
        ResearcherResponseDto<ResearcherDto> response = ResearcherResponseDto.<ResearcherDto>builder().researcher(researcherDtos).build();
        return ResponseEntity.ok().body(response);
    }

    @Getter
    static class ProfessorDto {
        private final Long id;
        private final String name;
        private final String image;
        private final String location;
        private final String doctorate;
        private final String number;

        public ProfessorDto(final Professor professor) {
            this.id = professor.getId();
            this.name = professor.getName();
            this.image = professor.getImage();
            this.location = professor.getOffice();
            this.doctorate = professor.getDoctorate();
            this.number = professor.getNumber();
        }
    }

    @Getter
    static class GraduateDto {
        private final Long id;
        private final String name;
        private final String image;
        private final String location;
        private final LocalDateTime admission;

        public GraduateDto(final Graduate gradate) {
            this.id = gradate.getId();
            this.name = gradate.getName();
            this.image = gradate.getImage();
            this.location = gradate.getOffice();
            this.admission = gradate.getAdmission();
        }
    }

    @Getter
    static class UndergraduateDto {
        private final Long id;
        private final String name;
        private final String image;
        private final String location;
        private final LocalDateTime admission;

        public UndergraduateDto(final Undergraduate underGradate) {
            this.id = underGradate.getId();
            this.name = underGradate.getName();
            this.image = underGradate.getImage();
            this.location = underGradate.getOffice();
            this.admission = underGradate.getAdmission();
        }
    }

    @Getter
    static class CommitteeDto {
        private final Long id;
        private final String name;
        private final String image;
        private final String location;
        private final String position;

        public CommitteeDto(final Committee committee) {
            this.id = committee.getId();
            this.name = committee.getName();
            this.image = committee.getImage();
            this.location = committee.getOffice();
            this.position = committee.getPosition();
        }
    }

    @Getter
    static class ResearcherDto {
        private final Long id;
        private final String name;
        private final String image;
        private final String location;

        public ResearcherDto(final Researcher researcher) {
            this.id = researcher.getId();
            this.name = researcher.getName();
            this.image = researcher.getImage();
            this.location = researcher.getOffice();
        }
    }
}
