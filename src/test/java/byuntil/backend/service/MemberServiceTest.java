package byuntil.backend.service;

import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.repository.MemberRepository;
import byuntil.backend.member.dto.request.MemberUpdateRequestDto;
import byuntil.backend.member.dto.request.ProfessorSaveRequestDto;
import byuntil.backend.member.service.MemberService;
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
        ProfessorSaveRequestDto professor = ProfessorSaveRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("나승훈")
                .major("asdfasdfsa")
                .doctorate("A")
                .location("전주")
                .number("01096574723")
                .build();

        //when
        Long id = memberService.saveMember(professor);

        //then
        Member member = (Member) memberService.findOneMember(id).get();
        Assertions.assertThat(member.getName()).isEqualTo(professor.getName());

    }

    @Test
    public void 멤버삭제() throws Exception {
        //given
        ProfessorSaveRequestDto professor = ProfessorSaveRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("나승훈")
                .major("asdfasdfsa")
                .doctorate("A")
                .location("전주")
                .number("01096574723")
                .build();
        Long id = memberService.saveMember(professor);
        //when
        Member beforeMember = (Member) memberService.findOneMember(id).get();
        memberService.delete(beforeMember.getId());
        //then
        Assertions.assertThat(memberService.findAllMember()).isEmpty();
    }

    @Test
    public void 멤버업데이트() throws Exception {
        //given
        ProfessorSaveRequestDto professor = ProfessorSaveRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("나승훈")
                .major("asdfasdfsa")
                .doctorate("A")
                .location("전주")
                .number("01096574723")
                .build();
        Long id = memberService.saveMember(professor);

        MemberUpdateRequestDto updateMember = MemberUpdateRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("구성재")
                .major("asdfasdfsa")
                .doctorate("A")
                .location("서울")
                .number("01096574723")
                .build();
        Member beforeMember = (Member) memberService.findOneMember(id).get();
        //when
        memberService.updateMember(id, updateMember);
        //then
        Member afterMember = (Member) memberService.findOneMember(id).get();
        System.out.println("beforeMember.getName() = " + beforeMember.getName());
        System.out.println("afterMember.getName() = " + afterMember.getName());
    }

}