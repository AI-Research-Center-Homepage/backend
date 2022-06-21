package byuntil.backend.research.domain.repository;

import byuntil.backend.research.domain.entity.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ThesisRepository extends JpaRepository<Thesis, Long> {
    @Modifying
    @Query("DELETE FROM Thesis t WHERE t IN(SELECT thesisList FROM Field f JOIN f.thesisList thesisList WHERE f.name = :name)")
    void deleteByFieldName(@Param("name") String name);
}
