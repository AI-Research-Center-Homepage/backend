package byuntil.backend.research.domain.entity;

import byuntil.backend.member.domain.entity.Member_Thesis;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.research.dto.ThesisDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;


@Entity
@Getter
@Builder
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

    /*아래 다대다 테이블있음
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;*/

    @OneToMany(mappedBy = "thesis")
    private List<Member_Thesis> members = new ArrayList<>();

    public void addMemberThesis(Member_Thesis memberThesis){
        members.add(memberThesis);
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