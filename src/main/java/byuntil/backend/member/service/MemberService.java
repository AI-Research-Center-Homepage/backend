package byuntil.backend.member.service;

import byuntil.backend.member.domain.entity.member.*;
import byuntil.backend.member.domain.repository.MemberRepository;
import byuntil.backend.member.dto.request.MemberSaveRequestDto;
import byuntil.backend.member.dto.request.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveMember(MemberSaveRequestDto dto) {
        Member member = dto.toEntity();
        return ((Member) memberRepository.save(member)).getId();
    }

    public Optional findOneMember(Long id) {
        return memberRepository.findById(id);
    }

    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    @Transactional
    public void updateMember(Long id, MemberUpdateRequestDto requestDto) {
        Member member = (Member) memberRepository.findById(id).get();
        member.update(requestDto.getName(), requestDto.getEmail(), requestDto.getMajor(), requestDto.getImage());

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
            researcher.update(requestDto.getResearch());
        } else if (member instanceof Undergraduate) {
            Undergraduate undergraduate = (Undergraduate) member;
            undergraduate.update(requestDto.getAdmission(), requestDto.getResearch());
        }
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.delete(memberRepository.findById(id).get());
    }
}
