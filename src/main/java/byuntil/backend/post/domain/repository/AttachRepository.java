package byuntil.backend.post.domain.repository;

import byuntil.backend.post.domain.entity.Attach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachRepository extends JpaRepository<Attach, Long> {
}
