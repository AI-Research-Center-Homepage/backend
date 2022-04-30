package byuntil.backend.research.dto;

import byuntil.backend.research.domain.entity.Project;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
    private Long id;
    private String name;

    public Project toEntity(){
        Project project = Project.builder().name(name).build();
        return project;
    }
}
