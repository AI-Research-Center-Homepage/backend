package byuntil.backend.member.service;

import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;

    @Transactional
    public void saveMember(Member member) {
        repository.save(member);
    }

    public List<Member> findMembers() {
        return repository.findAll();
    }

    public Optional findOneMember(Long memberId) {
        return repository.findById(memberId);
    }

}
