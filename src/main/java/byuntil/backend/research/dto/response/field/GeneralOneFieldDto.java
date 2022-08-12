package byuntil.backend.research.dto.response.field;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeneralOneFieldDto {
    private String name;
    private String description;
}
