package byuntil.backend.member.service;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.common.exception.IdNotExistException;
import byuntil.backend.common.exception.LoginIdDuplicationException;
import byuntil.backend.member.domain.entity.member.*;
import byuntil.backend.member.domain.repository.MemberRepository;
import byuntil.backend.member.dto.request.MemberSaveRequestDto;
import byuntil.backend.member.dto.request.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //탈퇴
    @Transactional
    public void secession(Long id) throws Throwable {
        //존재하지 않는 회원을 탈퇴시키려고 하는 경우 error 발생해야함
        if (!memberRepository.findById(id).isPresent()) {
            throw new IdNotExistException("존재하지 않는 id입니다");
        } else {
            Member member = (Member) memberRepository.findById(id).get();
            member.getLogin().setDeleted(true);


            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));

            member.getLogin().setLoginPw(generatedString);
            MemberUpdateRequestDto dto = MemberUpdateRequestDto.builder().admission(LocalDateTime.now()).email("del").major("del").doctorate("del")
                    .position("del").number("del").name("del").image("del").office("del").build();
            updateMember(member.getId(), dto);
        }
    }

    @Transactional
    public Long saveMember(MemberSaveRequestDto dto) {
        //dto를 entity로 만들고 admin도 entity로만든다음에 return함
        memberRepository.findByLoginId(dto.getLoginDto().getLoginId()).ifPresent((m -> {
            throw new LoginIdDuplicationException("이미 존재하는 회원입니다.");
        }));
        String originPW = dto.getLoginDto().getLoginPw();
        String encodedPW = passwordEncoder.encode(originPW);
        dto.getLoginDto().setLoginPw(encodedPW);

        return ((Member) memberRepository.save(dto.toEntity())).getId();
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
        String encodedPw = passwordEncoder.encode(member.getLogin().getLoginPw());
        member.getLogin().setLoginId(encodedPw);

        if (member instanceof Professor professor) {
            professor.update(requestDto.getDoctorate(), requestDto.getNumber());
        } else if (member instanceof Committee committee) {
            committee.update(requestDto.getPosition());
        } else if (member instanceof Graduate graduate) {
            graduate.update(requestDto.getAdmission());
        } else if (member instanceof Researcher) {
            Researcher researcher = (Researcher) member;
        } else if (member instanceof Undergraduate) {
            Undergraduate undergraduate = (Undergraduate) member;
            undergraduate.update(requestDto.getAdmission());
        }
    }

    @Transactional
    public void delete(Long id) throws Throwable {
        Member member = (Member) memberRepository.findById(id).get();
        memberRepository.delete(member);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByLoginId(username);
        if (!result.isPresent()) {
            throw new UsernameNotFoundException("Check Id");
        }
        Member member = result.get();

        Collection<SimpleGrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        LoginDto dto = new LoginDto(member.getLogin().getLoginId(), member.getLogin().getLoginPw(), auths,
                member.getLogin().getDeleted());
        return dto;
    }

    public List<Member> findAllByPosition(String position) {
        return memberRepository.findAllByPosition(position);
    }
}
