package byuntil.backend.entity;

import byuntil.backend.entity.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Thesis {
    @Id
    @GeneratedValue
    @Column(name = "THESIS_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "FIELD_ID")
    private Field field;

    private String title;

    private String ko_name;
    private String en_name;
    private String journal;
    private String publish;
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public Thesis(Long id, Field field, String title, String ko_name, String en_name, String journal, String publish, String url, Member member) {
        this.id = id;
        this.field = field;
        this.title = title;
        this.ko_name = ko_name;
        this.en_name = en_name;
        this.journal = journal;
        this.publish = publish;
        this.url = url;
        this.member = member;
    }
}
