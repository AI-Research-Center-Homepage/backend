package byuntil.backend.entity;

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
    private Category category;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Builder
    public Field(Long id, Category category, String description) {
        this.id = id;
        this.category = category;
        this.description = description;
    }
}