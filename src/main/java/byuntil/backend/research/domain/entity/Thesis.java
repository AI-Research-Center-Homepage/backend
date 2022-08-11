package byuntil.backend.research.domain.entity;

import byuntil.backend.member_thesis.entity.Member_Thesis;
import byuntil.backend.research.dto.request.MemberDto;
import byuntil.backend.research.dto.request.ThesisDto;
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


    @Column(nullable = false)
    private String title;

    private String koName;
    private String enName;
    private String journal;
    private LocalDateTime publishDate;
    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private Field field;

    @OneToMany(mappedBy = "thesis")
    private List<Member_Thesis> member_theses = new ArrayList<>();

    //builder어노테이션을 class단위로 하지 않은 이유 : 그러면 위의 member_thesis가 new할당을 안받아서 null이 됨
    @Builder
    public Thesis(final String title, final String koName, final String enName, final String journal, final LocalDateTime publishDate, final String url) {
        this.title = title;
        this.koName = koName;
        this.enName = enName;
        this.journal = journal;
        this.publishDate = publishDate;
        this.url = url;
    }

    public void deleteMemberThesis(){
        member_theses.clear();
    }

    //연관관계 설정 메서드
    public void setField(final Field field) {
        this.field = field;
    }

    public void addMemberThesis(final Member_Thesis memberThesis) {
        member_theses.add(memberThesis);
    }

    public void update(final ThesisDto dto) {
        this.title = dto.getTitle();
        this.koName = dto.getKoName();
        this.enName = dto.getEnName();
        this.journal = dto.getJournal();
        this.publishDate = dto.getPublishDate();
        this.url = dto.getUrl();

    }

    public ThesisDto toDto() {
        List<MemberDto> memberDtoList = new ArrayList<>();
        for (Member_Thesis memberThesis: member_theses) {
            Long id = memberThesis.getMember().getId();
            String name = memberThesis.getMember().getName();
            memberDtoList.add(MemberDto.builder().id(id).name(name).build());
        }
        return ThesisDto.builder().title(this.title).koName(this.koName).enName(this.enName).journal(this.journal)
                .publishDate(this.publishDate).url(this.url).fieldName(field.getName()).id(id).members(memberDtoList).build();

    }


}
