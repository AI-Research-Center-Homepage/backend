package byuntil.backend.post.domain.repository;

import byuntil.backend.post.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select m from Board m where m.name = :name")
    Optional<Board> findByName(@Param("name") String name);
}
