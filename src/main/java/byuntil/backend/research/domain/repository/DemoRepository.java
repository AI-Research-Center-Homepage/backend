package byuntil.backend.research.domain.repository;

import byuntil.backend.research.domain.entity.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoRepository extends JpaRepository<Demo, Long> {
}
