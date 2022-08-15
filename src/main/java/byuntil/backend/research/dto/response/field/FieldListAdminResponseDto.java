package byuntil.backend.research.dto.response.field;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class FieldListAdminResponseDto {
    private List<FieldAdminResponseDto> fields;
}
