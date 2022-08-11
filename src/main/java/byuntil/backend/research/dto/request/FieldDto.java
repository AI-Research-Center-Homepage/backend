package byuntil.backend.research.dto.request;

import byuntil.backend.research.domain.entity.Field;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FieldDto {
    private Long id;
    private final String name;
    private final String description;


    public Field toEntity() {
        return Field.builder().description(this.description).name(this.name).build();
    }
}
