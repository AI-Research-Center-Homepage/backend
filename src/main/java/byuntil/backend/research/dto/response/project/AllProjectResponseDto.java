package byuntil.backend.research.dto.response.project;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AllProjectResponseDto {
    private String fieldName;
    private List<OneProjectDto> projects;
}
