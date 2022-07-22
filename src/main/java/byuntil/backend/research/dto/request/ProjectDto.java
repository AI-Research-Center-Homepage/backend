package byuntil.backend.research.dto.request;

import byuntil.backend.research.domain.entity.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectDto {
    private Long id;
    private final String name;
    private final String description;
    private final String content;
    private final String participants;
    private final String fieldName;

    public Project toEntity() {
        return Project.builder().title(name).description(description).content(content).participants(participants).build();
    }
}
