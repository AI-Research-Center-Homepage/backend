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

}
