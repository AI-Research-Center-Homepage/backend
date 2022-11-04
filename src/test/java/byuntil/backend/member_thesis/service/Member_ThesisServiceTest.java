package byuntil.backend.member_thesis.service;

import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.dto.request.save.ProfessorSaveDto;
import byuntil.backend.member.service.MemberService;
import byuntil.backend.member_thesis.entity.Member_Thesis;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.dto.request.ThesisDto;
import byuntil.backend.research.service.ThesisService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class Member_ThesisServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ThesisService thesisService;
    @Autowired
    private Member_ThesisService member_thesisService;

    public ProfessorSaveDto makeMemberDto() {
        ProfessorSaveDto professor = ProfessorSaveDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("나승훈")
                .major("asdfasdfsa")
                .doctorate("A")
                .number("01096574723")
                .build();
        return professor;

    }

}