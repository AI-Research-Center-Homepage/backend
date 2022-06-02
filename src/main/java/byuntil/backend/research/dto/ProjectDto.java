package byuntil.backend.research.dto;

import byuntil.backend.research.domain.entity.Project;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
    private Long id;
    private String name;
    private String content;
    private String participants;
    private FieldDto fieldDto;

    public Project toEntity(){
        Project project = Project.builder().name(name).content(content).participants(participants).build();
        return project;
    }
}
