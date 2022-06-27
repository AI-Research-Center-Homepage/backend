package byuntil.backend.research.dto;

import byuntil.backend.research.domain.entity.Project;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private String content;
    private String participants;
    private String fieldName;

    public Project toEntity() {
        Project project = Project.builder().name(name).description(description).content(content).participants(participants).build();
        return project;
    }
}
