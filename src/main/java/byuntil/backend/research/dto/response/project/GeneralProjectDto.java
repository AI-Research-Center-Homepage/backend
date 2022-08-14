package byuntil.backend.research.dto.response.project;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GeneralProjectDto {
    private String fieldName;
    private List<GeneralOneProjectDto> projects;
}
