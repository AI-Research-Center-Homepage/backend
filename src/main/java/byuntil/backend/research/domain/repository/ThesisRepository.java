package byuntil.backend.research.domain.repository;

import byuntil.backend.research.domain.entity.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThesisRepository extends JpaRepository<Thesis, Long> {
    @Modifying
    @Query("DELETE FROM Thesis t WHERE t IN(SELECT thesisList FROM Field f JOIN f.thesisList thesisList WHERE f.name = :name)")
    void deleteByFieldName(@Param("name") String name);

    @Query("SELECT t FROM Thesis t join t.field f where f.name = :name")
    List<Thesis> findAllByFieldName(@Param("name") String name);
}
