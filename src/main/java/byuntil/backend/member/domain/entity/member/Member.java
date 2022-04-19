package byuntil.backend.member.domain.entity.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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

    private String name;

    private String major;

    private String email;

    private String image;

    protected Member() {

    }

    @Column(name = "DTYPE", insertable = false, updatable = false)
    private String dtype;

    public String getDtype() {
        return dtype;
    }
    /*@OneToMany(mappedBy = "member")
    private List<Member_Thesis> theses = new ArrayList<>();

    //private String image;
    //연관관계의 주인이 아닌쪽을 mappedby 속성 지정
    @OneToMany(mappedBy = "member")
    private List<Image> profile = new ArrayList<>();

    //project
    @OneToMany(mappedBy = "project")
    private List<Image> images = new ArrayList<>();*/

    //field 추가해주세요

    public void update(String name, String email, String major, String image) {
        this.name = name;
        this.email = email;
        this.major = major;
        this.image = image;
    }
}
