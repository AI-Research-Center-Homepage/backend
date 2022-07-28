package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controlller.domain.Login;
import byuntil.backend.member.dto.request.MemberUpdateRequestDto;
import byuntil.backend.member_thesis.entity.Member_Thesis;
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

    private String image;//imageurl

    private String location;

    @Embedded
    private Login login;

    public void changePw(final String encodedPw) {
        this.login.setLoginPw(encodedPw);
    }
//

    protected Member() {

    }

    @Column(name = "DTYPE", insertable = false, updatable = false)
    private String dtype;

    public Member(final String name, final String major, final String email, final String image, final String dtype, final String location, final Login login) {
        this.name = name;
        this.major = major;
        this.email = email;
        this.image = image;
        this.dtype = dtype;
        this.location = location;
        this.login = login;
        //this.role = UserRole.valueOf(admin.getAuthorities().toArray()[0].toString());
    }

    public String getDtype() {
        return dtype;
    }

    @OneToMany(mappedBy = "member")
    private List<Member_Thesis> member_theses = new ArrayList<>();


    public void update(final MemberUpdateRequestDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.major = dto.getMajor();
        this.image = dto.getImage();
        this.location = dto.getLocation();
        this.login.setLoginId(dto.getLoginDto().getLoginId());
        this.login.setLoginPw(dto.getLoginDto().getLoginPw());
        //this.login.setRole(UserRole.valueOf(dto.getLoginDto().getAuthorities().toArray()[0].toString()));
        this.login.setDeleted(dto.getLoginDto().getDeleted());
    }

    public void addMemberThesis(final Member_Thesis memberThesis) {
        this.member_theses.add(memberThesis);
    }
}
