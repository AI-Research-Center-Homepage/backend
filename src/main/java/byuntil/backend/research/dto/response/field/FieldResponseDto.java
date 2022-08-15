package byuntil.backend.research.dto.response.field;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FieldResponseDto {
    private String fieldName;
    private String description;
}
