package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.domain.Admin;
import byuntil.backend.admin.domain.UserRole;
import byuntil.backend.member.domain.entity.Member_Thesis;
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

    private String image;//imageurl

    private String office;

    private String fields;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Admin admin;

    protected Member() {

    }

    @Column(name = "DTYPE", insertable = false, updatable = false)
    private String dtype;
    public Member(String name, String major, String email, String image, String dtype, String office, String fields, Admin admin) {
        this.name = name;
        this.major = major;
        this.email = email;
        this.image = image;
        this.dtype = dtype;
        this.office = office;
        this.fields = fields;
        this.admin = admin;
    }

    public String getDtype() {
        return dtype;
    }
    @OneToMany(mappedBy = "member")
    private List<Member_Thesis> theses = new ArrayList<>();

    public void setAdmin(Admin admin){
        this.admin = admin;
    }

    public void update(MemberUpdateRequestDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.major = dto.getMajor();
        this.image = dto.getImage();
        this.office = dto.getOffice();
        this.fields = dto.getFields();
        this.admin.setLoginId(dto.getAdminDto().getLoginId());
        this.admin.setRole(UserRole.valueOf(dto.getAdminDto().getAuthorities().toArray()[0].toString()));
    }
    public void addMemberThesis(Member_Thesis memberThesis){
        this.theses.add(memberThesis);
    }
}
