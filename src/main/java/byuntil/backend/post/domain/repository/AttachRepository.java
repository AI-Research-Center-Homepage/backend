package byuntil.backend.post.domain.repository;

import byuntil.backend.post.domain.entity.Attach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttachRepository extends JpaRepository<Attach, Long> {
}
