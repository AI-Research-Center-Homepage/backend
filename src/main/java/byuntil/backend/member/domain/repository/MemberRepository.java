package byuntil.backend.member.domain.repository;

import byuntil.backend.member.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository<T extends Member> extends JpaRepository<T, Long> {

    @Query("select m from Member m where m.login.loginId = :loginId")
    Optional<Member> findByLoginId(@Param("loginId") String loginId);

    @Query("select m from Member m where m.dtype = :position")
    List<Member> findAllByDtype(@Param("position") String position);
}