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

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "field")
    private Demo demo;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "field")
    private Project project;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "field")
    private Thesis thesis;

    public void setProject(Project project){
        this.project = project;
    }
    public void setDemo(Demo demo){
        this.demo = demo;
    }
    public void setThesis(Thesis thesis){
        this.thesis = thesis;
    }


    @Builder
    public Field(String name,  String description) {
        this.name = name;
        this.description = description;
    }
    public void update(String name, String description){
        this.name = name;
        this.description = description;
    }
    public FieldDto toDto(){
        FieldDto dto = FieldDto.builder().name(name).description(description).build();
        return dto;

    }
}