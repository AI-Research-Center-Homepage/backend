package byuntil.backend.member.domain.repository;

import byuntil.backend.member.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
