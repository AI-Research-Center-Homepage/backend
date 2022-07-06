package byuntil.backend.research.dto.request;

import byuntil.backend.research.domain.entity.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ProjectDto {
    private final Long id;
    private final String name;
    private final String description;
    private final String content;
    private final String participants;
    private final String fieldName;


    public ProjectDto(final Project project) {
        this.id = project.getId();
        this.name = project.getTitle();
        this.description = project.getDescription();
        this.content = project.getContent();
        this.participants = project.getParticipants();
        this.fieldName = project.getField().getName();
    }

    public Project toEntity() {
        return Project.builder().title(name).description(description).content(content).participants(participants).build();
    }
}
