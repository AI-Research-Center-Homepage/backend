package byuntil.backend.research.dto.response.project;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProjectResponseDto<T> {
    private final List<T> projects;
}
