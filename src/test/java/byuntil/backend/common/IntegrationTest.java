package byuntil.backend.common;

import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.repository.MemberRepository;
import byuntil.backend.member.dto.request.ProfessorSaveRequestDto;
import byuntil.backend.member.service.MemberService;
import byuntil.backend.member_thesis.entity.Member_Thesis;
import byuntil.backend.member_thesis.service.Member_ThesisService;
import byuntil.backend.post.domain.entity.Board;
import byuntil.backend.post.domain.entity.Post;
import byuntil.backend.post.domain.repository.BoardRepository;
import byuntil.backend.post.dto.PostDto;
import byuntil.backend.research.domain.entity.Category;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.dto.FieldDto;
import byuntil.backend.research.dto.ProjectDto;
import byuntil.backend.research.dto.ThesisDto;
import byuntil.backend.research.service.ThesisService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
public class IntegrationTest {
    @Autowired
    Member_ThesisService member_thesisService;
    @Autowired
    MemberService memberService;
    @Autowired
    ThesisService thesisService;
    @Autowired
    BoardRepository boardRepository;

    @Test
    @Transactional
    @Commit
    //member 여러명 thesis 여러명 해서 다대다 test(thesis, member, field)
    public void thesis_member_field(){
        //given
        ProfessorSaveRequestDto dto1 = makeProfessorDto();
        ProfessorSaveRequestDto dto2 = makeProfessorDto();
        ThesisDto thesisDto1 = makeThesisDto();
        ThesisDto thesisDto2 = makeThesisDto();
        FieldDto fieldDto1 = makeFieldDto();
        //when
        Long memberId = memberService.saveMember(dto1);
        Member member = (Member)memberService.findOneMember(memberId).get();

        Long memberId2 = memberService.saveMember(dto2);
        Member member2 = (Member)memberService.findOneMember(memberId2).get();

        //field를 thesis에 넣기
        thesisDto1.addFieldDto(fieldDto1);
        Long thesisId1 = thesisService.save(thesisDto1);
        Thesis thesis1 = thesisService.findById(thesisId1).get();

        //field를 안넣은 thesis
        Long thesisId2 = thesisService.save(thesisDto2);
        Thesis thesis2 = thesisService.findById(thesisId2).get();

        Long MTId1 = member_thesisService.createThesis(memberId, thesisId1);
        Long MTId2 = member_thesisService.createThesis(memberId2, thesisId2);

        Member_Thesis memberThesis1 =member_thesisService.findById(MTId1).get();
        Member_Thesis memberThesis2 =member_thesisService.findById(MTId2).get();
        //then

        //1. 중간테이블로 매핑이 잘되었는가?
        Assertions.assertThat(memberThesis1.getMember().getId()).isEqualTo(memberId);
        //2. field가 제대로 넣어졌는가?
        Assertions.assertThat(memberThesis1.getThesis().getField().getId()).isEqualTo(thesis1.getField().getId());
        //3. field가 안넣어졌는가?
        Assertions.assertThat(memberThesis2.getThesis().getField()).isNull();
    }
    //attach를 포함한 post를 member가 올리기
    @Transactional
    @Commit
    @Test
    public void attach_post_board_member(){
        //given
        //board는 이미 추가해놓은 상태임
        PostDto postDto = makePostDto();
        //when
        //then
    }
    public PostDto makePostDto(){
        return PostDto.builder().title("제목1").content("내용11").build();
    }

    public FieldDto makeFieldDto(){
        return FieldDto.builder().category(Category.AR_AI).description(""+Math.random()).build();
    }
    public ThesisDto makeThesisDto(){
        return ThesisDto.builder().enName(""+Math.random()).koName("한글이름").journal("sss")
                .title("ss").publishDate(LocalDateTime.now()).url("sda/ss").build();
    }
    public ProfessorSaveRequestDto makeProfessorDto(){

        ProfessorSaveRequestDto professor = ProfessorSaveRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("홍길동" + Math.random())
                .major("asdfasdfsa")
                .doctorate("A")
                .location("전주")
                .number("0109222723")
                .build();
        return professor;
    }
}
