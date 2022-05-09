package byuntil.backend.member.domain.entity.member;

import byuntil.backend.member_thesis.entity.Member_Thesis;
import byuntil.backend.member.dto.request.MemberUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@Getter
@AllArgsConstructor
@DynamicUpdate
public abstract class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String major;

    private String email;

    private String image;

    protected Member() {

    }

    @Column(name = "DTYPE", insertable = false, updatable = false)
    private String dtype;

    public Member(String name, String major, String email, String image, String dtype) {
        this.name = name;
        this.major = major;
        this.email = email;
        this.image = image;
        this.dtype = dtype;
    }

    public String getDtype() {
        return dtype;
    }
    @OneToMany(mappedBy = "member")
    private List<Member_Thesis> member_theses = new ArrayList<>();


    public void update(MemberUpdateRequestDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.major = dto.getMajor();
        this.image = dto.getImage();
    }
    public void addMemberThesis(Member_Thesis memberThesis){
        this.member_theses.add(memberThesis);
    }
}
