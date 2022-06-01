package byuntil.backend.member.service;

import byuntil.backend.admin.domain.Admin;
import byuntil.backend.admin.repository.AdminRepository;
import byuntil.backend.admin.service.UserDetailService;
import byuntil.backend.common.exception.LoginIdDuplicationException;
import byuntil.backend.member.domain.entity.member.*;
import byuntil.backend.member.domain.repository.MemberRepository;
import byuntil.backend.member.dto.request.MemberSaveRequestDto;
import byuntil.backend.member.dto.request.MemberUpdateRequestDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final UserDetailService userDetailService;

    //탈퇴
    @Transactional
    public void secession(Long id) throws Throwable {
        Member member = (Member)memberRepository.findById(id).get();
        member.getAdmin().setDeleted(true);

        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        member.getAdmin().setLoginId(generatedString);
        MemberUpdateRequestDto dto = MemberUpdateRequestDto.builder().admission("del").email("del").major("del").doctorate("del")
                .research("del").position("del").number("del").name("del").image("del").location("del").adminDto(member.getAdmin().toDto()).build();
        updateMember(member.getId(), dto);
    }
    @Transactional
    public Long saveMember(MemberSaveRequestDto dto) {
        //dto를 entity로 만들고 admin도 entity로만든다음에 return함
        userDetailService.findByLoginId(dto.getAdminDto().getLoginId()).ifPresent((m -> {
            throw new LoginIdDuplicationException("이미 존재하는 회원입니다.");
        }));
        //dto를 모두 entity로 만드는 메서드
        Admin admin = dto.dtosToEntity();
        userDetailService.signUp(admin);
        memberRepository.save(admin.getMember());

        return admin.getMember().getId();
    }

    public Optional findOneMember(Long id) {
        return memberRepository.findById(id);
    }

    public Professor findOneProfessor(Long id) {
        return (Professor) memberRepository.findById(id).get();
    }

    public Graduate findOneGraduate(Long id) {
        return (Graduate) memberRepository.findById(id).get();
    }

    public Researcher findOneResearcher(Long id) {
        return (Researcher) memberRepository.findById(id).get();
    }

    public Undergraduate findOneUndergraduate(Long id) {
        return (Undergraduate) memberRepository.findById(id).get();
    }

    public Committee findOneCommittee(Long id) {
        return (Committee) memberRepository.findById(id).get();
    }


    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    @Transactional
    public void updateMember(Long id, MemberUpdateRequestDto requestDto) throws Throwable {
        Member member = (Member) memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        member.update(requestDto);
        //그리고 암호화를 해주어야한다
        userDetailService.encodedPw(member.getAdmin());

        if (member instanceof Professor) {
            Professor professor = (Professor) member;
            professor.update(requestDto.getDoctorate(), requestDto.getLocation(), requestDto.getNumber());
        } else if (member instanceof Committee) {
            Committee committee = (Committee) member;
            committee.update(requestDto.getPosition());
        } else if (member instanceof Graduate) {
            Graduate graduate = (Graduate) member;
            graduate.update(requestDto.getAdmission());
        } else if (member instanceof Researcher) {
            Researcher researcher = (Researcher) member;
        } else if (member instanceof Undergraduate) {
            Undergraduate undergraduate = (Undergraduate) member;
            undergraduate.update(requestDto.getAdmission(), requestDto.getResearch());
        }
    }

    @Transactional
    public void delete(Long id) throws Throwable {
        Member member = (Member) memberRepository.findById(id).get();
        memberRepository.delete(member);
        //연관되어있는 admin도 삭제
        userDetailService.deleteById(member.getAdmin().getId());
    }
}
