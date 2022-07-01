package byuntil.backend.member_thesis.entity;

import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.research.domain.entity.Thesis;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
public class Member_Thesis {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_THESIS_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "THESIS_ID")
    private Thesis thesis;

    //정적팩터리메서드
    public static Member_Thesis createThesis(final Member member, final Thesis thesis) {
        Member_Thesis memberThesis = new Member_Thesis();
        memberThesis.setMember(member);
        memberThesis.setThesis(thesis);
        return memberThesis;
    }

    private void setThesis(final Thesis thesis) {
        this.thesis = thesis;
        thesis.addMemberThesis(this);
    }

    private void setMember(final Member member) {
        this.member = member;
        member.addMemberThesis(this);
    }
}
