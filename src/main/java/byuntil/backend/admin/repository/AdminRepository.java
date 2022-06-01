package byuntil.backend.admin.repository;

import byuntil.backend.admin.domain.Admin;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("select m from Admin m where m.loginId = :loginId")
    Optional<Admin> findByLoginId(@Param("loginId") String loginId);
}
