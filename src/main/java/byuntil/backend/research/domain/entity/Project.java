package byuntil.backend.research.domain.entity;

import byuntil.backend.research.dto.ProjectDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
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
    private String name;

    public void update(ProjectDto projectDto){
        this.name = projectDto.getName();
    }


}