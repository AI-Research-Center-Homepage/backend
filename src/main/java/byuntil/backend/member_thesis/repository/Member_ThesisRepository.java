package byuntil.backend.member_thesis.repository;

import byuntil.backend.member_thesis.entity.Member_Thesis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Member_ThesisRepository extends JpaRepository<Member_Thesis, Long> {
    //memberId 에 해당하는 member_thesis 삭제
    void deleteByMemberId(Long memberId);
    //thesisId 에 해당하는 member_thesis 삭제
    void deleteByThesisId(Long thesisId);
}
