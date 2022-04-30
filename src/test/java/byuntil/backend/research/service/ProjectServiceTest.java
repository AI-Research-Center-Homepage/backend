package byuntil.backend.research.service;

import byuntil.backend.research.domain.entity.Project;
import byuntil.backend.research.dto.ProjectDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceTest {
    @Autowired
    private ProjectService projectService;

    @Test
    public void 프로젝트저장(){
        //given
        ProjectDto dto = ProjectDto.builder().name("홍길동").build();
        //when
        Long id = projectService.save(dto);
        Project project = projectService.findById(id).get();
        //then
        Assertions.assertThat(project.getName()).isEqualTo(dto.getName());
    }
    @Test
    public void 프로젝트수정(){
        //given
        ProjectDto origin = ProjectDto.builder().name("홍길동").build();
        ProjectDto update = ProjectDto.builder().name("수정된이름").build();
        //when
        Long id = projectService.save(origin);
        projectService.update(id, update);
        Project project = projectService.findById(id).get();
        //then
        Assertions.assertThat(project.getName()).isEqualTo(update.getName());
    }
    @Test
    public void 프로젝트삭제(){
        //given
        ProjectDto dto = ProjectDto.builder().name("홍길동").build();
        //when
        Long id = projectService.save(dto);
        projectService.delete(id);
        Optional<Project> project = projectService.findById(id);
        //then
        Assertions.assertThat(project).isNotPresent();
    }
    @Test
    public void 프로젝트전체조회(){
        //given
        ProjectDto project1 = ProjectDto.builder().name("프로젝트1").build();
        ProjectDto project2 = ProjectDto.builder().name("프로젝트2").build();
        //when
        projectService.save(project1);
        projectService.save(project2);
        int size = projectService.findAll().size();
        //then
        Assertions.assertThat(size).isEqualTo(2);

    }

}