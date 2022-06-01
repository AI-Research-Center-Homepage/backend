package byuntil.backend.research.domain.entity;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private Field field;

    @Builder
    public Demo(String name, String content, String url, String participants) {
        this.name = name;
        this.content = content;
        this.url = url;
        this.participants = participants;
    }

    //연관관계 설정 메서드
    public void addField(Field field){
        this.field = field;
        field.setDemo(this);
    }

    public Demo() {

    }
}
