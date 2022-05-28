package byuntil.backend.research.domain.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
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

    public Demo() {

    }
}
