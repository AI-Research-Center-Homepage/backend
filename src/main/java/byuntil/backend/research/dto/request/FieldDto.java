package byuntil.backend.research.dto.request;

import byuntil.backend.research.domain.entity.Field;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FieldDto {
    private final String fieldName;
    private final String description;


    public Field toEntity() {
        return Field.builder().description(this.description).name(this.fieldName).build();
    }
}
