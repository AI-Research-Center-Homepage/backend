package byuntil.backend.research;

import byuntil.backend.common.exception.ExistException;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Project;
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
        return FieldDto.builder().name("field1").description("설명1").build();
    }

    public FieldDto makeETCFieldDto() {
        return FieldDto.builder().name("field2").description("설명2").build();
    }


    @Test
    @Transactional
    @Commit
    public void 삭제테스트() {
        //given
        FieldDto fieldDto = makeFieldDto();
        DemoDto demoDto = makeDemoDto();
        ProjectDto projectDto = makeProjectDto();
        //when
        fieldRepository.save(fieldDto.toEntity());
        demoService.save(demoDto);
        projectService.save(projectDto);
        Field field = fieldRepository.findByName(fieldDto.getName()).get();
        //post를 돌아가면서 해당 field에 속하면 모두 삭제하는.. sql을 작성해야할듯?


        thesisRepository.deleteByFieldName(field.getName());
        projectRepository.deleteByFieldName(field.getName());
        fieldRepository.deleteByName(field.getName());

        em.flush();
        em.clear();//이거 없으면 영속성컨텍스트에서 가져오기때문에 테스트 실패

        //then
        assertThat(fieldService.findById(field.getId())).isNotPresent();
    }

    @Test
    @Transactional
    public void 삭제시예외() {
        //given
        FieldDto fieldDto = makeFieldDto();
        ProjectDto projectDto = makeProjectDto();
        //when
        fieldRepository.save(fieldDto.toEntity());
        Long projectId = projectService.save(projectDto);
        ProjectDto projectDto2 = projectService.findById(projectId);
        Field field = fieldRepository.findByName(projectDto.getFieldName()).get();

        //then
        Assertions.assertThrows(ExistException.class, () -> {
            fieldService.deleteById(fieldService.findByName(projectDto2.getFieldName()).get().getId());
        });
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
