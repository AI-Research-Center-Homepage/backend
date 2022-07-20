package byuntil.backend.member.controller.common;
import static org.mockito.Mockito.when;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.repository.MemberRepository;
import byuntil.backend.member.dto.request.MemberAllInfoDto;
import byuntil.backend.member.dto.request.ProfessorSaveRequestDto;
import byuntil.backend.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static byuntil.backend.common.factory.MockMemberFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MemberMockControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("멤버 등록")
    void joinMember() throws Exception {
        //given
        MemberAllInfoDto dto = makeMemberAllInfoDto();
        //when
        //then
        mockMvc.perform(post("/api/v1/admin/members/new").param("position", "Professor")
                        .param("research", "연구분야")
                        .param("name", "이름")
                        .param("email","이메일")
                        .param("location", "위치")
                        .param("admission", "200--200")
                        .param("major", "으악")
                        .param("image", "d"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("멤버 조회")
    void readMember(){
        //given

        //when
        //then

    }

    @Test
    @DisplayName("멤버 탈퇴")
    void resignMember(){
        //given
        //when
        //then
    }

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
    public MemberAllInfoDto makeMemberAllInfoDto(){
        return MemberAllInfoDto.builder().research("연구분야").doctorate("doc").email("sad@naver.com").name("김김김")
                .image("url~~").location("전주시").admission(LocalDateTime.now()).loginDto(new LoginDto("dd", "dd")).number("012-120-222").position("position").build();

    }
}