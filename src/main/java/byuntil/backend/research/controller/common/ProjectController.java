package byuntil.backend.research.controller.common;

import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Project;
import byuntil.backend.research.dto.response.ProjectResponseDto;
import byuntil.backend.research.service.FieldService;
import byuntil.backend.research.service.ProjectService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProjectController {
    private final FieldService fieldService;
    private final ProjectService projectService;

    @GetMapping("/project")
    public ResponseEntity<?> readProjects() {
        List<Field> fields = fieldService.findAll();
        List<ProjectFieldDto> projectFieldDtos = new ArrayList<>();
        for (Field field : fields) {
            String fieldName = field.getName();
            List<Project> projects = projectService.findAllByFieldName(fieldName);

            List<ProjectDto> projectDtos = projects.stream().map(ProjectDto::new).toList();
            projectFieldDtos.add(ProjectFieldDto.builder().fieldName(fieldName).projects(projectDtos).build());
        }

        ProjectResponseDto<ProjectFieldDto> response = ProjectResponseDto.<ProjectFieldDto>builder().projects(projectFieldDtos).build();

        return ResponseEntity.ok().body(response);
    }

    @Getter
    static class ProjectDto {
        private final String title;
        private final String description;
        private final String content;
        private final String participants;

        public ProjectDto(final Project project) {
            this.title = project.getTitle();
            this.description = project.getDescription();
            this.content = project.getContent();
            this.participants = project.getParticipants();
        }
    }

    @Getter
    @RequiredArgsConstructor
    @Builder
    static class ProjectFieldDto {
        private final String fieldName;
        private final List<ProjectDto> projects;
    }
}
