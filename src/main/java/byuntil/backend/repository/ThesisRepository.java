package byuntil.backend.repository;

import byuntil.backend.entity.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThesisRepository extends JpaRepository<Thesis, Long> {
}
