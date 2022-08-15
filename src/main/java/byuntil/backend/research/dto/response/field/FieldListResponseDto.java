package byuntil.backend.research.dto.response.field;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FieldListResponseDto {
    private List<FieldResponseDto> fields;
}
