package byuntil.backend.research.dto;

import byuntil.backend.research.domain.entity.Field;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class FieldDto {
    private final Long id;
    private final String name;
    private final String description;

    public FieldDto(final Field field) {
        this.id = field.getId();
        this.name = field.getName();
        this.description = field.getDescription();
    }

    public Field toEntity() {
        return Field.builder().description(this.description).name(this.name).build();
    }
}
