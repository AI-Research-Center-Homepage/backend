package byuntil.backend.research.dto;

import byuntil.backend.research.domain.entity.Category;
import byuntil.backend.research.domain.entity.Field;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class FieldDto {
    private Long id;
    private Category category;
    private String description;
    public Field toEntity(){
        Field field = Field.builder().description(this.description).category(this.category).build();
        return field;
    }
}
