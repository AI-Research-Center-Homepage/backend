package byuntil.backend.research.dto.response.field;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FieldAdminResponseDto {
    private Long id;
    private String fieldName;
    private String description;
}
