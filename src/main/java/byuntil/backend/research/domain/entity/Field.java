package byuntil.backend.research.domain.entity;

import byuntil.backend.research.dto.request.FieldDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Field {
    @Id
    @GeneratedValue
    @Column(name = "FIELD_ID")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "field")
    private List<Project> projectList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "field")
    private List<Thesis> thesisList = new ArrayList<>();

    public void addProject(final Project project) {
        project.setField(this);
        this.projectList.add(project);
    }

    public void addThesis(final Thesis thesis) {
        thesis.setField(this);
        this.thesisList.add(thesis);
    }

    @Builder
    public Field(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public void update(final FieldDto fieldDto) {
        this.name = fieldDto.getName();
        this.description = fieldDto.getDescription();

    }

    public FieldDto toDto() {
        FieldDto dto = FieldDto.builder().name(name).description(description).build();
        return dto;

    }
}