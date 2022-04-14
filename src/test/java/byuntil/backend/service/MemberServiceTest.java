package byuntil.backend.service;

import byuntil.backend.entity.member.Member;
import byuntil.backend.entity.member.Professor;
import byuntil.backend.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    public void 멤버저장() throws Exception {
        //given
        Professor professor = Professor.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("asdfasfdas")
                .major("asdfasdfsa")
                .doctorate("A")
                .location("전주")
                .number("01096574723")
                .build();

        memberService.saveMember(professor);

        //when
        List<Member> membersList = memberRepository.findAll();

        //then
        Assertions.assertThat(membersList.get(0).getName()).isEqualTo(professor.getName());

    }

    @Test
    public void 멤버삭제() throws Exception {
        //given
        Professor professor = Professor.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("asdfasfdas")
                .major("asdfasdfsa")
                .doctorate("A")
                .location("전주")
                .number("01096574723")
                .build();

        memberService.saveMember(professor);
        //when
        memberRepository.delete(professor);
        List<Member> members = memberRepository.findAll();
        //then
        Assertions.assertThat(members.isEmpty()).isTrue();

    }

    @Test
    public void 멤버정보업데이트() throws Exception {
        //given
        Professor professor = Professor.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("나승훈")
                .major("asdfasdfsa")
                .doctorate("A")
                .location("전주")
                .number("01096574723")
                .build();

        memberService.saveMember(professor);
        //when
        Member beforeMember = memberRepository.findById(professor.getId()).get();
        beforeMember.setName("구성재"); //나중에 바꿔야함 이거 setter로 임의로 바꿔놓은 것..
        //then
        Member afterMember = memberRepository.findById(professor.getId()).get();
        Assertions.assertThat(beforeMember.getName()).isNotEqualTo(afterMember.getName());
        System.out.println("afterMember.getId() = " + afterMember.getId());
        System.out.println("beforeMember.getId() = " + beforeMember.getId());
    }
}