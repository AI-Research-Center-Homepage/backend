package byuntil.backend.common.member.service;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Professor;
import byuntil.backend.member.dto.request.ProfessorSaveRequestDto;
import byuntil.backend.member.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    @Commit
    @Transactional//영속성컨텍스트 범위에 있어야 지연로딩이 가능해져서 이 어노테이션이 필요함
    public void 새로운멤버저장(){
        //given
        ProfessorSaveRequestDto professorSaveRequestDto = makeMemberDto();
        //when
        Long id =  memberService.saveMember(professorSaveRequestDto);
        Professor professor = (Professor)memberService.findOneMember(id).get();
        //then
        Assertions.assertTrue(professor.getEmail().equals(professorSaveRequestDto.getEmail()));


    }
    public ProfessorSaveRequestDto makeMemberDto(){
        LoginDto loginDto = new LoginDto("id1", "111",false);
              //  LoginDto.builder().username("id1").password("111").authorities(auths).build();이렇게쓰면 에러발생 -> in unnamed module of loader 'app')

        ProfessorSaveRequestDto professor = ProfessorSaveRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("나승훈")
                .major("asdfasdfsa")
                .doctorate("A")
                .number("01096574723")
                .fields("여러가지 연구분야 에이아이 등등등")
                .loginDto(loginDto)
                .office("주소주소")
                .build();

        return professor;
    }

}