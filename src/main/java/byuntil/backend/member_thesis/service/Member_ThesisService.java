package byuntil.backend.member_thesis.service;

import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.repository.MemberRepository;
import byuntil.backend.member_thesis.entity.Member_Thesis;
import byuntil.backend.member_thesis.repository.Member_ThesisRepository;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.domain.repository.ThesisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class Member_ThesisService {
    private final Member_ThesisRepository member_thesisRepository;
    private final MemberRepository memberRepository;
    private final ThesisRepository thesisRepository;
    @Transactional
    public Long createThesis(Long memberId, Long thesisId){
        Member member = (Member) memberRepository.findById(memberId).get();
        Thesis thesis = thesisRepository.findById(thesisId).get();

        Member_Thesis memberThesis = Member_Thesis.createThesis(member, thesis);
        member_thesisRepository.save(memberThesis);
        return memberThesis.getId();
    }
    @Transactional
    public void delete(Long id){
        member_thesisRepository.deleteById(id);
    }
    public Optional<Member_Thesis> findById(Long id){
        return member_thesisRepository.findById(id);
    }
    public List<Member_Thesis> findAll(){
        return member_thesisRepository.findAll();
    }
}
