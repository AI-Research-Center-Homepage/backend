package byuntil.backend.research.domain.entity;

import byuntil.backend.research.dto.DemoDto;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Demo {
    @Id
    @GeneratedValue
    @Column(name = "DEMO_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private String url;

    private String participants;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private Field field;

    @Builder
    public Demo(String name, String content, String url, String participants) {
        this.name = name;
        this.content = content;
        this.url = url;
        this.participants = participants;
    }

    public void setField(Field field){
        this.field = field;
    }
    public void update(DemoDto demoDto){
        this.name = demoDto.getName();
        this.content = demoDto.getContent();
        this.url = demoDto.getUrl();
        this.participants = demoDto.getParticipants();
    }

    public Demo() {

    }
}
