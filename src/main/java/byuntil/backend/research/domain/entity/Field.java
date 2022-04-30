package byuntil.backend.research.domain.entity;

import byuntil.backend.research.dto.FieldDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Field {
    @Id
    @GeneratedValue
    @Column(name = "FIELD_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Builder
    public Field(Category category, String description) {
        this.category = category;
        this.description = description;
    }
    public void update(Category category, String description){
        this.category = category;
        this.description = description;
    }
    public FieldDto toDto(){
        FieldDto dto = FieldDto.builder().category(category).description(description).build();
        return dto;

    }
}