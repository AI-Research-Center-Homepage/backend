package byuntil.backend.research.domain.repository;

import byuntil.backend.research.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
