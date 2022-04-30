package byuntil.backend.research.domain.repository;

import byuntil.backend.research.domain.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field,Long> {
}
