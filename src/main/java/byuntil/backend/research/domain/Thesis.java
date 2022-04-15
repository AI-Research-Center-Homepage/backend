package byuntil.backend.research.domain;

import byuntil.backend.member.domain.entity.Member_Thesis;
import byuntil.backend.member.domain.entity.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private String koName;
    private String enName;
    private String journal;
    private LocalDateTime publishDate;
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public Thesis(Long id, Field field, String title, String koName, String enName, String journal, LocalDateTime publishDate, String url, Member member) {
        this.id = id;
        this.field = field;
        this.title = title;
        this.koName = koName;
        this.enName = enName;
        this.journal = journal;
        this.publishDate = publishDate;
        this.url = url;
        this.member = member;
    }

    @OneToMany(mappedBy = "thesis")
    private List<Member_Thesis> members = new ArrayList<>();
}
