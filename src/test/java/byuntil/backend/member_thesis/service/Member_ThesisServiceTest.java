package byuntil.backend.member_thesis.service;

import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.dto.request.ProfessorSaveRequestDto;
import byuntil.backend.member.service.MemberService;
import byuntil.backend.member_thesis.entity.Member_Thesis;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.dto.ThesisDto;
import byuntil.backend.research.service.ThesisService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class Member_ThesisServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ThesisService thesisService;
    @Autowired
    private Member_ThesisService member_thesisService;

    @Test
    //1. 멤버저장
    //2. 논문저장
    //3. member_thesisService에 멤버-논문 매핑
    public void 멤버가논문쓸때(){
        //given
        ProfessorSaveRequestDto professor = makeMemberDto();
        ThesisDto thesisDto = makeThesisDto();

        //when
        Long memberId = memberService.saveMember(professor);
        Long thesisId = thesisService.save(thesisDto);
        Member member = (Member)memberService.findOneMember(memberId).get();
        Thesis thesis = thesisService.findById(thesisId).get();
        Long id = member_thesisService.createThesis(memberId, thesisId);
        Member_Thesis memberThesis = member_thesisService.findById(id).get();

        //then
        //중간테이블로 thesis에 잘 접근할 수 있는가?
        Assertions.assertThat(memberThesis.getThesis().getTitle()).isEqualTo(thesis.getTitle());
        //member가 가지고 있는 중간테이블, thesis가 가지고있는 중간테이블이 일치하는가?
        Assertions.assertThat(member.getMember_theses().get(0).getId())
                .isEqualTo(memberThesis.getId())
                .isEqualTo(thesis.getMember_theses().get(0).getId());

    }
    public ThesisDto makeThesisDto(){
        return ThesisDto.builder().enName("minji").koName("민지").url("https://localhost:8080").journal("journal1")
                .title("제목1").publishDate(LocalDateTime.now()).build();
    }
    public ProfessorSaveRequestDto makeMemberDto(){
        ProfessorSaveRequestDto professor = ProfessorSaveRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("나승훈")
                .major("asdfasdfsa")
                .doctorate("A")
                .location("전주")
                .number("01096574723")
                .build();
        return professor;

    }

}