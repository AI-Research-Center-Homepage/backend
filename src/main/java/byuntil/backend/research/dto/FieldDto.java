package byuntil.backend.research.dto;

import byuntil.backend.research.domain.entity.Field;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldDto {
    private Long id;
    private String name;
    private String description;
    public Field toEntity(){
        Field field = Field.builder().description(this.description).name(this.name).build();
        return field;
    }
}
