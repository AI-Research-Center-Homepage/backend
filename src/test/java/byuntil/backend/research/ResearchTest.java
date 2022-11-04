package byuntil.backend.research;

import byuntil.backend.common.exception.ExistException;
import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.repository.DemoRepository;
import byuntil.backend.research.domain.repository.FieldRepository;
import byuntil.backend.research.domain.repository.ProjectRepository;
import byuntil.backend.research.domain.repository.ThesisRepository;
import byuntil.backend.research.dto.request.DemoDto;
import byuntil.backend.research.dto.request.FieldDto;
import byuntil.backend.research.dto.request.ProjectDto;
import byuntil.backend.research.service.DemoService;
import byuntil.backend.research.service.FieldService;
import byuntil.backend.research.service.ProjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ResearchTest {
    @Autowired
    DemoService demoService;
    @Autowired
    ProjectService projectService;
    @Autowired
    DemoRepository demoRepository;
    @Autowired
    ThesisRepository thesisRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    FieldService fieldService;
    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    EntityManager em;

    public DemoDto makeDemoDto() {
        return DemoDto.builder().content("내s용d").title("데모1").url("url").build();
    }

    public ProjectDto makeProjectDto() {
        return ProjectDto.builder().name("fie").fieldName("field1").description("요약").content("내용123").participants("참여자들").build();
    }

    public ProjectDto makeProjectETCDto() {
        return ProjectDto.builder().name("fie").fieldName("field2").description("요약").content("내용123").participants("참여자들").build();
    }

    public FieldDto makeFieldDto() {
        return FieldDto.builder().fieldName("field1").description("설명1").build();
    }

    public FieldDto makeETCFieldDto() {
        return FieldDto.builder().fieldName("field2").description("설명2").build();
    }


   @Test
   @Transactional
   public void test(){
        Demo d = Demo.builder().title("제목1").build();
        demoRepository.save(d);//1차 캐시에 저장
       //jpql 실행 전 flush실행 -> db에 커밋을 날림
       //디비에 현재 제목1에 대한 demo가 저장된 상태
       em.createQuery("update Demo d set d.title=:title where d.id=:id")
               .setParameter("id", d.getId())
               .setParameter("title", "새제목")
               .executeUpdate();
       //업데이트쿼리를 날림. jpql은 디비에 있는 데이터에 대해 update쿼리를 날림
       //조회를 하면 1차캐시에서 부터 찾기때문에 update쿼리의 영향을 안받은 "제목1"의 demo 엔티티를 가져오게됨
       Demo newDemo = demoRepository.getById(d.getId());
       System.out.println(newDemo.getTitle());
   }

    @Test
    @Transactional
    @DisplayName("findAllByFieldName() 테스트 (필드명 같을때)")
    public void 필드명이_같을때() {
        FieldDto fieldDto = makeFieldDto();
        fieldRepository.save(fieldDto.toEntity());

        ProjectDto projectDto1 = makeProjectDto();
        ProjectDto projectDto2 = makeProjectDto();
        Long project1Id = projectService.save(projectDto1);
        Long project2Id = projectService.save(projectDto2);

        List<ProjectDto> projects = projectService.findAllByFieldName("field1");

        assertThat(project1Id).isNotEqualTo(project2Id);
        Assertions.assertEquals(2, projects.size());
    }

    @Test
    @Transactional
    @DisplayName("findAllByFieldName() 테스트 (필드명 다를때)")
    public void 필드명이_다를때() {
        FieldDto fieldDto1 = makeFieldDto();
        FieldDto fieldDto2 = makeETCFieldDto();
        fieldRepository.save(fieldDto1.toEntity());
        fieldRepository.save(fieldDto2.toEntity());

        ProjectDto projectDto1 = makeProjectDto();
        ProjectDto projectDto2 = makeProjectETCDto();
        ProjectDto projectDto3 = makeProjectETCDto();
        projectService.save(projectDto1);
        projectService.save(projectDto2);
        projectService.save(projectDto3);

        List<ProjectDto> projects1 = projectService.findAllByFieldName("field1");
        List<ProjectDto> projects2 = projectService.findAllByFieldName("field2");


        Assertions.assertEquals(1, projects1.size());
        Assertions.assertEquals(2, projects2.size());
    }
}
