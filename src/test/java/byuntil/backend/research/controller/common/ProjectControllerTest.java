package byuntil.backend.research.controller.common;

import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Project;
import byuntil.backend.research.dto.FieldDto;
import byuntil.backend.research.dto.ProjectDto;
import byuntil.backend.research.service.FieldService;
import byuntil.backend.research.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static byuntil.backend.common.factory.MockFieldFactory.createMockFieldDtos;
import static byuntil.backend.common.factory.MockFieldFactory.createMockFields;
import static byuntil.backend.common.factory.MockResearchFactory.createMockProjectDtos;
import static byuntil.backend.common.factory.MockResearchFactory.createMockProjects;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ProjectControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProjectService projectService;

    @Autowired
    FieldService fieldService;

    @Test
    @Transactional
    @DisplayName("프로젝트 전체 조회 테스트")
    void readProjects() throws Exception {
        int FIELD_NUM = 2;
        int PROJECT_NUM = 5;
        //Project, Field
        List<Project> mockProjects = createMockProjects(FIELD_NUM, PROJECT_NUM);
        List<Field> mockFields = createMockFields(FIELD_NUM);

        //각각 DTO로 변환
        List<FieldDto> mockFieldDtos = createMockFieldDtos(mockFields);
        List<ProjectDto> mockProjectDtos = createMockProjectDtos(mockProjects);

        //save
        mockFieldDtos.stream().map(FieldDto -> fieldService.save(FieldDto)).toList();
        mockProjectDtos.stream().map(ProjectDto -> projectService.save(ProjectDto)).toList();


        mockMvc.perform(get("/api/v1/project")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}