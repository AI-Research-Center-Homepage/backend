package byuntil.backend.entity.member;

import byuntil.backend.entity.Member_Thesis;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private String major;
    private String email;
    private String image;

    @OneToMany(mappedBy = "member")
    private List<Member_Thesis> theses = new ArrayList<>();
}
