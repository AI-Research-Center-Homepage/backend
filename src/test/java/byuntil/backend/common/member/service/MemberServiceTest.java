package byuntil.backend.common.member.service;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.entity.member.Professor;
import byuntil.backend.member.dto.request.MemberSaveRequestDto;
import byuntil.backend.member.dto.request.ProfessorSaveRequestDto;
import byuntil.backend.member.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    @Commit
    @Transactional//영속성컨텍스트 범위에 있어야 지연로딩이 가능해져서 이 어노테이션이 필요함
    public void 새로운멤버저장() {
        //given
        ProfessorSaveRequestDto professorSaveRequestDto = makeMemberDto();
        //when
        Long id = memberService.saveMember(professorSaveRequestDto);
        Professor professor = (Professor) memberService.findOneMember(id).get();
        //then
        Assertions.assertTrue(professor.getEmail().equals(professorSaveRequestDto.getEmail()));


    }

    public ProfessorSaveRequestDto makeMemberDto() {
        LoginDto loginDto = new LoginDto("id1", "111", false);
        //  LoginDto.builder().username("id1").password("111").authorities(auths).build();이렇게쓰면 에러발생 -> in unnamed module of loader 'app')

        ProfessorSaveRequestDto professor = ProfessorSaveRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("나승훈")
                .major("asdfasdfsa")
                .doctorate("A")
                .number("01096574723")
                .loginDto(loginDto)
                .location("주소주소")
                .build();

        return professor;
    }

    @Test
    @Transactional
    @DisplayName("findAllByPosition() 메서드 테스트")
    public void 직책별_멤버_검색() throws Exception {
        //given
        ProfessorSaveRequestDto professorSaveRequestDto = makeMemberDto();
        Long id = memberService.saveMember(professorSaveRequestDto);
        Professor oneProfessor = memberService.findOneProfessor(id);

        //when
        List<ProfessorSaveRequestDto> professors = (List<ProfessorSaveRequestDto>) memberService.findAllByPosition("Professor");
        //List<Professor> professors = members.stream().map(member -> (Professor) member).toList();

        //then
        Assertions.assertEquals(oneProfessor.getNumber(), professors.get(0).getNumber());
    }
}