package byuntil.backend.research.domain.repository;

import byuntil.backend.research.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Modifying
    @Query("DELETE FROM Project p WHERE p IN(SELECT projectList FROM Field f JOIN f.projectList projectList WHERE f.name = :name)")
    void deleteByFieldName(@Param("name") String name);
}
