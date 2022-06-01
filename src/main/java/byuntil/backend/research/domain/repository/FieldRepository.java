package byuntil.backend.research.domain.repository;

import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Project;
import byuntil.backend.research.domain.entity.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FieldRepository extends JpaRepository<Field,Long> {
    @Query("select m from Field m where m.name = :name")
    Optional<Field> findByName(@Param("name") String name);

    //demo의 field만 가져오는 쿼리 작성
    @Query("SELECT a FROM Field a INNER JOIN Demo as d ON a.id = d.field.id")
    List<Optional<Field>> allDemoFields();

    @Query("SELECT a FROM Field a INNER JOIN Project as d ON a.id = d.field.id")
    List<Optional<Project>> allProjectFields();

    @Query("SELECT a FROM Field a INNER JOIN Thesis as d ON a.id = d.field.id")
    List<Optional<Thesis>> allThesisFields();


}
