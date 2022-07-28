package byuntil.backend.member.controller.common;

import byuntil.backend.member.domain.entity.member.*;
import byuntil.backend.member.dto.response.*;
import byuntil.backend.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
    public ResponseEntity readProfessors() {
        List<ProfessorDto> professorDtos = (List<ProfessorDto>) memberService.findAllByPosition(MemberDtype.Professor.toString());
        ProfessorResponseDto<ProfessorDto> response = ProfessorResponseDto.<ProfessorDto>builder().professor(professorDtos).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/graduate")
    public ResponseEntity readGraduates() {
        List<GraduateDto> graduateDtos = (List<GraduateDto>) memberService.findAllByPosition(MemberDtype.Graduate.toString());
        GraduateResponseDto<GraduateDto> response = GraduateResponseDto.<GraduateDto>builder().graduate(graduateDtos).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/undergraduate")
    public ResponseEntity readUnderGraduates() {
        List<UndergraduateDto> undergraduateDtos = (List<UndergraduateDto>) memberService.findAllByPosition(MemberDtype.Undergraduate.toString());
        UndergraduateResponseDto<UndergraduateDto> response = UndergraduateResponseDto.<UndergraduateDto>builder().undergraduate(undergraduateDtos).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/committee")
    public ResponseEntity readCommittee() {
        List<CommitteeDto> committeeDtos = (List<CommitteeDto>) memberService.findAllByPosition(MemberDtype.Committee.toString());
        CommitteeResponseDto<CommitteeDto> response = CommitteeResponseDto.<CommitteeDto>builder().committee(committeeDtos).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/researcher")
    public ResponseEntity readResearcher() {
        List<ResearcherDto> researcherDtos  = (List<ResearcherDto>) memberService.findAllByPosition(MemberDtype.Researcher.toString());
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
            this.location = professor.getLocation();
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
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private final LocalDateTime admission;

        public GraduateDto(final Graduate gradate) {
            this.id = gradate.getId();
            this.name = gradate.getName();
            this.image = gradate.getImage();
            this.location = gradate.getLocation();
            this.admission = gradate.getAdmission();
        }
    }

    @Getter
    static class UndergraduateDto {
        private final Long id;
        private final String name;
        private final String image;
        private final String location;
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private final LocalDateTime admission;

        public UndergraduateDto(final Undergraduate underGradate) {
            this.id = underGradate.getId();
            this.name = underGradate.getName();
            this.image = underGradate.getImage();
            this.location = underGradate.getLocation();
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
            this.location = committee.getLocation();
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
            this.location = researcher.getLocation();
        }
    }
}
