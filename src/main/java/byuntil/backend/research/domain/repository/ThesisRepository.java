package byuntil.backend.research.domain.repository;

import byuntil.backend.research.domain.entity.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThesisRepository extends JpaRepository<Thesis, Long> {
}
