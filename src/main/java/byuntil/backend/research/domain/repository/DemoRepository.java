package byuntil.backend.research.domain.repository;

import byuntil.backend.research.domain.entity.Demo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DemoRepository extends JpaRepository<Demo, Long> {
    @Modifying
    @Query("DELETE FROM Demo d WHERE d IN(SELECT demoList FROM Field f JOIN f.demoList demoList WHERE f.name = :name)")
        //@Query("DELETE FROM Demo d WHERE d.field.name = :name")
    void deleteByFieldName(@Param("name") String name);
}
