package byuntil.backend.research.domain.entity;

import byuntil.backend.research.dto.DemoDto;
import byuntil.backend.research.dto.FieldDto;
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

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "field")
    private Demo demo;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "field")
    private Project project;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "field")
    private Thesis thesis;

    public void addDemo(Demo demo){
        demo.setField(this);
        this.demo = demo;
    }
    public void addProject(Project project){
        project.setField(this);
        this.project = project;
    }
    public void addThesis(Thesis thesis){
        thesis.setField(this);
        this.thesis = thesis;
    }

    @Builder
    public Field(String name,  String description) {
        this.name = name;
        this.description = description;
    }
    public void update(FieldDto fieldDto){
        this.name = fieldDto.getName();
        this.description = fieldDto.getDescription();
    }
    public FieldDto toDto(){
        FieldDto dto = FieldDto.builder().name(name).description(description).build();
        return dto;

    }
}