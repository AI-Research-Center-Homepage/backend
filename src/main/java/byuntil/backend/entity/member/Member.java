package byuntil.backend.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private String major;
    private String email;
    private String image;

    @Builder
    public Member(Long id, String name, String major, String email, String image) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.email = email;
        this.image = image;
    }
}
