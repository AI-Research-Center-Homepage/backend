package byuntil.backend.common;

import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.dto.request.save.ProfessorSaveDto;
import byuntil.backend.member.service.MemberService;
import byuntil.backend.member_thesis.entity.Member_Thesis;
import byuntil.backend.member_thesis.service.Member_ThesisService;
import byuntil.backend.post.domain.repository.BoardRepository;
import byuntil.backend.post.dto.PostDto;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.dto.request.FieldDto;
import byuntil.backend.research.dto.request.ThesisDto;
import byuntil.backend.research.service.ThesisService;
import byuntil.backend.s3.service.S3ServiceImpl;
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

    @Autowired
    S3ServiceImpl s3Service;

    @Test
    public void url반환(){
        String url = "s3://aihomepage/028bd8da84d54db6b84449ad8ad44487.jpg";
        String key = s3Service.urlToKey(url);
        Assertions.assertThat(key).isEqualTo("028bd8da84d54db6b84449ad8ad44487");
    }

    @Test
    @Transactional
    @Commit
    //member 여러명 thesis 여러명 해서 다대다 test(thesis, member, field)
    public void thesis_member_field() {
        //given
        ProfessorSaveDto dto1 = makeProfessorDto();
        ProfessorSaveDto dto2 = makeProfessorDto();
        ThesisDto thesisDto1 = makeThesisDto();
        ThesisDto thesisDto2 = makeThesisDto();
        FieldDto fieldDto1 = makeFieldDto();
        //when
        Long memberId = memberService.saveMember(dto1);
        Member member = (Member) memberService.findOneMember(memberId).get();

        Long memberId2 = memberService.saveMember(dto2);
        Member member2 = (Member) memberService.findOneMember(memberId2).get();

        //field를 thesis에 넣기
        Long thesisId1 = thesisService.save(thesisDto1);
        Thesis thesis1 = thesisService.findById(thesisId1).get();

        //field를 안넣은 thesis
        Long thesisId2 = thesisService.save(thesisDto2);
        Thesis thesis2 = thesisService.findById(thesisId2);

        Long MTId1 = member_thesisService.createThesis(memberId, thesisId1);
        Long MTId2 = member_thesisService.createThesis(memberId2, thesisId2);

        Member_Thesis memberThesis1 = member_thesisService.findById(MTId1).get();
        Member_Thesis memberThesis2 = member_thesisService.findById(MTId2).get();
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
    public void attach_post_board_member() {
        //given
        //board는 이미 추가해놓은 상태임
        PostDto postDto = makePostDto();
        //when
        //then
    }

    public PostDto makePostDto() {
        return PostDto.builder().title("제목1").boardName("board1").content("내용11").build();
    }

    public FieldDto makeFieldDto() {
        return FieldDto.builder().name("field").description("" + Math.random()).build();
    }

    public ThesisDto makeThesisDto() {
        return ThesisDto.builder().enName("" + Math.random()).koName("한글이름").journal("sss")
                .title("ss").fieldName("field1").publishDate(LocalDateTime.now()).url("sda/ss").build();
    }

    public ProfessorSaveDto makeProfessorDto() {

        ProfessorSaveDto professor = ProfessorSaveDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("홍길동" + Math.random())
                .major("asdfasdfsa")
                .doctorate("A")
                .number("0109222723")
                .build();
        return professor;
    }
}
