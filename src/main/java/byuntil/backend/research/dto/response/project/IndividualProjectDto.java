package byuntil.backend.research.dto.response.project;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IndividualProjectDto {
    private String name;
    private String description;
    private String content;
    private String participants;
    private String fieldName;

}
