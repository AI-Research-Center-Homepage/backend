package byuntil.backend.entity.member;

import byuntil.backend.entity.Member_Thesis;
import byuntil.backend.entity.Image;

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

    //private String image;
    //연관관계의 주인이 아닌쪽을 mappedby 속성 지정
    @OneToMany(mappedBy = "member")
    private List<Image> profile = new ArrayList<>();

    //project
    @OneToMany(mappedBy = "project")
    private List<Image> images = new ArrayList<>();

    //field 추가해주세요
}
