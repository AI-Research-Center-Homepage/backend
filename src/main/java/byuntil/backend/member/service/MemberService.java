package byuntil.backend.member.service;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.common.exception.IdNotExistException;
import byuntil.backend.common.exception.LoginIdDuplicationException;
import byuntil.backend.member.domain.entity.member.*;
import byuntil.backend.member.domain.repository.MemberRepository;
import byuntil.backend.member.dto.request.MemberSaveRequestDto;
import byuntil.backend.member.dto.request.MemberUpdateRequestDto;
import byuntil.backend.member.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
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
    public void secession(final Long id) throws Throwable {
        //존재하지 않는 회원을 탈퇴시키려고 하는 경우 error 발생해야함
        if (memberRepository.findById(id).isEmpty()) {
            throw new IdNotExistException("존재하지 않는 id입니다");
        } else {
            Member member = (Member) memberRepository.findById(id).get();
            //여기서 member가 login을 가지고 있는지도 확인해야함
            if(!Optional.ofNullable(member.getLogin()).isPresent()){
                throw new IdNotExistException("존재하지 않는 id입니다");
            }
            member.getLogin().setDeleted(true);


            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, StandardCharsets.UTF_8);

            member.getLogin().setLoginPw(generatedString);
            MemberUpdateRequestDto dto = MemberUpdateRequestDto.builder().admission(LocalDateTime.now()).email("del").major("del").doctorate("del")
                    .position("del").number("del").name("del").image("del").location("del").build();
            updateMember(member.getId(), dto);
        }
    }

    @Transactional
    public Long saveMember(final MemberSaveRequestDto dto) {
        //dto를 entity로 만들고 admin도 entity로만든다음에 return함
        //loginDto가 null일 경우 처리를 해주어야함
        Optional<String> id = Optional.ofNullable(dto)
                .map(MemberSaveRequestDto::getLoginDto)
                .map(LoginDto::getLoginId);

        id.ifPresentOrElse(x -> memberRepository.findByLoginId(id.get()).ifPresentOrElse(m -> {
            throw new LoginIdDuplicationException("이미 존재하는 회원입니다.");} ,
                ()-> dto.getLoginDto().setLoginPw(passwordEncoder.encode(dto.getLoginDto().getLoginPw())
                        )
                )
                ,()->{}
        );


        return ((Member) memberRepository.save(dto.toEntity())).getId();
    }

    public Optional findOneMember(final Long id) {
        return memberRepository.findById(id);
    }

    public Professor findOneProfessor(final Long id) {
        return (Professor) memberRepository.findById(id).get();
    }

    public Graduate findOneGraduate(final Long id) {
        return (Graduate) memberRepository.findById(id).get();
    }

    public Researcher findOneResearcher(final Long id) {
        return (Researcher) memberRepository.findById(id).get();
    }

    public Undergraduate findOneUndergraduate(final Long id) {
        return (Undergraduate) memberRepository.findById(id).get();
    }

    public Committee findOneCommittee(final Long id) {
        return (Committee) memberRepository.findById(id).get();
    }


    public List<MemberResponseDto> findAllMember() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponseDto> memberResponseDtoList = new ArrayList<>();
        for (Member member: members) {
            memberResponseDtoList.add(MemberResponseDto.builder().name(member.getName()).id(member.getId()).build());
        }
        return memberResponseDtoList;
    }

    @Transactional
    public void updateMember(final Long id, final MemberUpdateRequestDto requestDto) throws Throwable {
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
        } else if (member instanceof Undergraduate undergraduate) {
            undergraduate.update(requestDto.getAdmission());
        }
    }

    @Transactional
    public void delete(final Long id) throws Throwable {
        Member member = (Member) memberRepository.findById(id).get();
        memberRepository.delete(member);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByLoginId(username);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Check Id");
        }
        Member member = result.get();

        Collection<SimpleGrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        LoginDto dto = new LoginDto(member.getLogin().getLoginId(), member.getLogin().getLoginPw(), auths,
                member.getLogin().getDeleted());
        return dto;
    }

    public List<Member> findAllByPosition(final String position) {
        return memberRepository.findAllByPosition(position);
    }
}
