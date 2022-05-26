package byuntil.backend.research.domain.entity;

import byuntil.backend.member_thesis.entity.Member_Thesis;
import byuntil.backend.research.dto.ThesisDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Thesis {
    @Id
    @GeneratedValue
    @Column(name = "THESIS_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "FIELD_ID")
    private Field field;

    @Column(nullable = false)
    private String title;

    private String koName;
    private String enName;
    private String journal;
    private LocalDateTime publishDate;
    @Column(nullable = false)
    private String url;

    //builder로 만들면 밑에 new로 할당해준게 무시됨
    @OneToMany(mappedBy = "thesis")
    private List<Member_Thesis> member_theses = new ArrayList<>();

    //builder어노테이션을 class단위로 하지 않은 이유 : 그러면 위의 member_thesis가 new할당을 안받아서 null이 됨
    @Builder
    public Thesis(String title, String koName, String enName, String journal, LocalDateTime publishDate, String url){
        this.title = title;
        this.koName = koName;
        this.enName = enName;
        this.journal = journal;
        this.publishDate = publishDate;
        this.url = url;
    }

    public void addMemberThesis(Member_Thesis memberThesis){
        member_theses.add(memberThesis);
    }
    public void update(ThesisDto dto){
        this.title = dto.getTitle();
        this.koName = dto.getKoName();
        this.enName = dto.getEnName();
        this.journal = dto.getJournal();
        this.publishDate = dto.getPublishDate();
        this.url = dto.getUrl();

    }
    public void setField(Field field){
        this.field = field;
    }
    public void toDto(){
        ThesisDto dto = ThesisDto.builder().title(this.title).koName(this.koName).enName(this.enName).journal(this.journal)
                .publishDate(this.publishDate).url(this.url).build();

    }


}
