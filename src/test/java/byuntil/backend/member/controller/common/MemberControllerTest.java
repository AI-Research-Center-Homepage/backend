package byuntil.backend.member.controller.common;

import byuntil.backend.member.dto.request.ProfessorSaveRequestDto;
import byuntil.backend.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static byuntil.backend.common.factory.MockMemberFactory.createMockProfessorDtos;
import static byuntil.backend.common.factory.MockMemberFactory.createMockProfessors;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @Test
    void readProfessors() throws Exception {
        int PROFESSOR_NUM = 4;
        List<ProfessorSaveRequestDto> mockProfessorDtos = createMockProfessorDtos(createMockProfessors(PROFESSOR_NUM));

        mockProfessorDtos.stream().map(professorDto -> memberService.saveMember(professorDto)).toList();
        /*List<Member> allMembers = memberService.findAllByPosition(MemberDtype.Professor.toString());

        List<Professor> professors = allMembers.stream().map(member -> (Professor) member).toList();*/

        mockMvc.perform(get("/api/v1/professor")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}