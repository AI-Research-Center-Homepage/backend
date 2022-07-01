package byuntil.backend.research.domain.entity;

import byuntil.backend.research.dto.request.DemoDto;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Demo {
    @Id
    @GeneratedValue
    @Column(name = "DEMO_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private String url;

    private String participants;

    @Builder
    public Demo(final String title, final String description, final String content, final String url, final String participants) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.url = url;
        this.participants = participants;
    }

    public void update(final DemoDto demoDto) {
        this.title = demoDto.getTitle();
        this.description = demoDto.getDescription();
        this.content = demoDto.getContent();
        this.url = demoDto.getUrl();
        this.participants = demoDto.getParticipants();
    }

    public Demo() {

    }
}
