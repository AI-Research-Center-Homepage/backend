package byuntil.backend.research.dto.response.field;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class FieldDto {
    private final String fieldName;
}
