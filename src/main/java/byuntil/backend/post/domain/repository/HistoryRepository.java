package byuntil.backend.post.domain.repository;

import byuntil.backend.post.domain.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
