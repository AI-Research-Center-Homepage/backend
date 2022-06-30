package byuntil.backend.research.domain.entity;

import byuntil.backend.research.dto.request.ProjectDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {
    @Id
    @GeneratedValue
    @Column(name = "PROJECT_ID")
    private Long id;

    //Project : image = 1:n관계
    //아이거 mappedyby 아래 image에 project에 대한 멤버변수가 있어야 인식가능함 !

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private String participants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private Field field;

    //연관관계 설정 메서드
    public void setField(Field field) {
        this.field = field;
    }

    public void update(ProjectDto projectDto) {
        this.title = projectDto.getName();
        this.description = projectDto.getDescription();
        this.content = projectDto.getContent();
        this.participants = projectDto.getParticipants();
    }

    @Builder
    public Project(String title, String description, String content, String participants) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.participants = participants;
    }


}
