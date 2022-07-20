package byuntil.backend.common.factory;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Professor;
import byuntil.backend.member.dto.request.ProfessorSaveRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MockMemberFactory {
    public static Professor createMockVOProfessor(ProfessorSaveRequestVO professorVO) {
        return Professor.builder()
                .name(professorVO.name)
                .major(professorVO.major)
                .email(professorVO.email)
                .image(professorVO.image)
                .location(professorVO.location)
                .doctorate(professorVO.doctorate)
                .number(professorVO.number)
                .build();
    }
    public static Professor createMockProfessor() {
        return Professor.builder()
                .name("NAME")
                .major("MAJOR")
                .email("EMAIL")
                .image("IMAGE")
                .location("LOCATION")
                .doctorate("DOCTORATE")
                .number("NUM")
                .build();
    }

    public static List<Professor> createMockProfessors(int numPro) {
        List<Professor> professors = new ArrayList<>();
        for (int i = 0; i < numPro; i++) {
            ProfessorSaveRequestVO build = ProfessorSaveRequestVO.builder()
                    .name("이름" + i)
                    .major("전공" + i)
                    .email("이메일" + i)
                    .image("이미지주소" + i)
                    .location("오피스" + i)
                    .doctorate("학위" + i)
                    .number("번호" + i)
                    .build();
            professors.add(createMockVOProfessor(build));
        }
        return professors;
    }

    public static List<ProfessorSaveRequestDto> createMockProfessorDtos(List<Professor> professors) {
        List<ProfessorSaveRequestDto> dtos = new ArrayList<>();
        for (int i = 0; i < professors.size(); i++) {
            LoginDto loginDto = new LoginDto("id" + i, "A" + i, false);
            dtos.add(new ProfessorSaveRequestDto(professors.get(i), loginDto));
        }
        return dtos;
    }

    @Getter
    @Builder
    static class ProfessorSaveRequestVO {
        private String name;
        private String major;
        private String email;
        private String image;
        private String location;
        private String doctorate;
        private String number;
    }
}
