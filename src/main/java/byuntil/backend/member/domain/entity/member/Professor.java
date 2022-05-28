package byuntil.backend.member.domain.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor extends Member {
    @Column(nullable = false)
    private String doctorate;
    private String number;

    /*@Builder
    public Professor(String doctorate, String location, String number) {
        this.doctorate = doctorate;
        this.location = location;
        this.number = number;
    }*/

    @Builder
    public Professor(String name, String major, String email, String image, String dtype, String doctorate, String office, String number) {
        super(name, major, email, image, dtype, office);
        this.doctorate = doctorate;
        this.number = number;
    }

    public void update(String doctorate, String location, String number) {
        this.doctorate = doctorate;
        this.number = number;
    }
}
