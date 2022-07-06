package byuntil.backend.research.domain.repository;

import byuntil.backend.research.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Modifying
    @Query("DELETE FROM Project p WHERE p IN(SELECT projectList FROM Field f JOIN f.projectList projectList WHERE f.name = :name)")
    void deleteByFieldName(@Param("name") String name);

    //fieldName이 같은 모든 Project 가져오는 쿼리
    @Query("SELECT p from Project p join p.field f where f.name = :name")
    List<Project> findAllByFieldName(@Param("name") String name);
}
