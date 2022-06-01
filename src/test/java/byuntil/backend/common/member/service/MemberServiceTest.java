package byuntil.backend.common.member.service;

import byuntil.backend.admin.domain.Admin;
import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.admin.repository.AdminRepository;
import byuntil.backend.admin.service.UserDetailService;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.entity.member.Professor;
import byuntil.backend.member.domain.repository.MemberRepository;
import byuntil.backend.member.dto.request.MemberUpdateRequestDto;
import byuntil.backend.member.dto.request.ProfessorSaveRequestDto;
import byuntil.backend.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    MemberService memberService;
/*admin, member 합치고 나서 다시 test작성
    @Test
    @Commit
    @Transactional//영속성컨텍스트 범위에 있어야 지연로딩이 가능해져서 이 어노테이션이 필요함
    public void 멤버저장() throws Exception {
        //given
<<<<<<< HEAD
        ProfessorSaveRequestDto professor = ProfessorSaveRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("나승훈")
                .major("asdfasdfsa")
                .doctorate("A")
                .number("01096574723")
                .build();
=======
        ProfessorSaveRequestDto professor = createProfessorDto();
>>>>>>> develop

        //when
        //dto -> entity
        Long id = memberService.saveMember(professor);
        Professor member = memberService.findOneProfessor(id);

        adminRepository.findByLoginId(professor.getAdminDto().getLoginId());
        Admin foundAdmin = adminRepository.findByLoginId(professor.getAdminDto().getLoginId()).get();

        //then
        System.out.println("=============");
<<<<<<< HEAD
        Professor member = memberService.findOneProfessor(id);
        System.out.println(professor.getOffice() + " / " + professor.getDoctorate() + " / " + professor.getNumber());
        Assertions.assertThat(member.getName()).isEqualTo(professor.getName());
=======
        System.out.println(professor.getLocation() + " / " + professor.getDoctorate() + " / " + professor.getNumber());
>>>>>>> develop

        //dto -> entity변환
        Assertions.assertThat(member.getName()).isEqualTo(professor.getName());
        //연관관계 잘 저장 되어있는지
        Assertions.assertThat(member.getAdmin().getLoginId()).isEqualTo(foundAdmin.getLoginId());
        Assertions.assertThat(member.getAdmin().getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    @Transactional
    public void 멤버삭제() throws Throwable {
        //given
<<<<<<< HEAD
        ProfessorSaveRequestDto professor = ProfessorSaveRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("나승훈")
                .major("asdfasdfsa")
                .doctorate("A")
                .number("01096574723")
                .build();
=======
        ProfessorSaveRequestDto professor = createProfessorDto();
>>>>>>> develop
        Long id = memberService.saveMember(professor);
        //when
        Member beforeMember = (Member) memberService.findOneMember(id).get();
        String adminId = beforeMember.getAdmin().getLoginId();
        memberService.delete(beforeMember.getId());
        //then
        Assertions.assertThat(!adminRepository.findByLoginId(adminId).isPresent());
        Assertions.assertThat(!memberRepository.findById(beforeMember.getId()).isPresent());
        //Assertions.assertThat(memberService.findAllMember()).isEmpty();
    }

    @Test
    @Transactional
    @Commit
    public void 멤버업데이트() throws Throwable {
        //given
        //origin
        ProfessorSaveRequestDto originMemberDto = createProfessorDto();
        Long id = memberService.saveMember(originMemberDto);

        //new
        Collection<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        AdminDto updateAdminDto = new AdminDto("새로운 id", "새로운비번", auth, false);

        MemberUpdateRequestDto updateMemberDto = MemberUpdateRequestDto.builder()
                .email("변경후")
                .image("asdfasdfa")
                .name("홍길동2")
                .major("asdfasdfsa")
                .doctorate("A")
                .location("서울")
                .adminDto(updateAdminDto)
                .number("01096574723")
                .build();

        Member beforeMember = (Member) memberService.findOneMember(id).get();
        //when
        memberService.updateMember(id, updateMemberDto);
        Member member = (Member)memberService.findOneMember(id).get();
        Admin updateAdmin = userDetailService.findByLoginId(member.getAdmin().getLoginId()).get();
        Member afterMember = (Member) memberService.findOneMember(id).get();
        //then
        Assertions.assertThat(updateMemberDto.getName()).isEqualTo(afterMember.getName());
        Assertions.assertThat(updateAdminDto.getLoginId()).isEqualTo(updateAdmin.getLoginId());
    }
    @Test
    @Transactional
    @Commit
    public void 탈퇴() throws Throwable {

        //given
        ProfessorSaveRequestDto professor = createProfessorDto();

        Long memberId = memberService.saveMember(professor);
        Professor member = memberService.findOneProfessor(memberId);

        //when
        memberService.secession(memberId);

        //then
        Assertions.assertThat(member.getName()).isNotEqualTo(professor.getName());
        Assertions.assertThat(member.getAdmin().getDeleted()).isEqualTo(true);
        Assertions.assertThat(member.getAdmin().getLoginId()).isNotEqualTo(professor.getAdminDto().getLoginId());
    }
    public ProfessorSaveRequestDto createProfessorDto(){
        Collection<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        AdminDto adminDto = new AdminDto("newenwe", "pw1", auth, false);
        return ProfessorSaveRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("홍홍홍")
                .major("asdfasdfsa")
                .doctorate("A")
                .number("01096574723")
                .adminDto(adminDto)
                .build();
<<<<<<< HEAD
        Long id = memberService.saveMember(professor);

        MemberUpdateRequestDto updateMember = MemberUpdateRequestDto.builder()
                .email("asdfa")
                .image("asdfasdfa")
                .name("홍길동")
                .major("asdfasdfsa")
                .doctorate("A")
                .location("서울")
                .office("office1")
                .number("01096574723")
                .build();
        Member beforeMember = (Member) memberService.findOneMember(id).get();
        //when
        memberService.updateMember(id, updateMember);
        //then
        Member afterMember = (Member) memberService.findOneMember(id).get();
        Assertions.assertThat(beforeMember.getName()).isNotEqualTo(afterMember.getName());
=======
>>>>>>> develop
    }*/
}