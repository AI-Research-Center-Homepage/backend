package byuntil.backend.service;

import byuntil.backend.entity.member.Member;
import byuntil.backend.entity.member.Professor;
import byuntil.backend.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    public void 멤버저장() throws Exception {
        //given
        Member professor = createProfessor("A", "123", "hi");
        memberService.saveMember(professor);


        //when


        //then
        Assertions.assertThat(oneMember).isEqualTo(professor);

    }

    private Professor createProfessor(String doctorate, String location, String number) {
        
    }

}