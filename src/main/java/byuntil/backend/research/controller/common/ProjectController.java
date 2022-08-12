package byuntil.backend.research.controller.common;

import byuntil.backend.research.dto.request.FieldDto;
import byuntil.backend.research.dto.request.ProjectDto;
import byuntil.backend.research.dto.response.project.GeneralOneProjectDto;
import byuntil.backend.research.dto.response.project.GeneralProjectDto;
import byuntil.backend.research.dto.response.project.ProjectResponseDto;
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
    public ResponseEntity readProjects() {
        List<FieldDto> fields = fieldService.findAll();
        List<GeneralProjectDto> projectFieldDtos = new ArrayList<>();
        for (FieldDto field : fields) {
            String fieldName = field.getName();
            List<ProjectDto> projectDtos = projectService.findAllByFieldName(fieldName);
            List<GeneralOneProjectDto> list = new ArrayList<>();
            for (ProjectDto dto: projectDtos) {
                list.add(GeneralOneProjectDto.builder().title(dto.getName()).id(dto.getId()).participants(dto.getParticipants())
                        .content(dto.getContent()).description(dto.getDescription()).build());
            }
            projectFieldDtos.add(GeneralProjectDto.builder().fieldName(fieldName).projects(list).build());
        }

        return ResponseEntity.ok().body(projectFieldDtos);
    }

}
