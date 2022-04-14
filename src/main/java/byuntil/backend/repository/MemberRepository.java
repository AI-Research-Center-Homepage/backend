package byuntil.backend.repository;

import byuntil.backend.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository<T extends Member> extends JpaRepository<T, Long> {
}
