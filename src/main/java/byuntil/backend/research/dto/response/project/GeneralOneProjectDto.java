package byuntil.backend.research.dto.response.project;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeneralOneProjectDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private String participants;
}
