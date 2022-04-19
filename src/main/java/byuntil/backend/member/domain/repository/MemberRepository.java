package byuntil.backend.member.domain.repository;

import byuntil.backend.member.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository<T extends Member> extends JpaRepository<T, Long> {
    Optional<T> findById(Long id);
}