package byuntil.backend.member.domain.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Undergraduate extends Member {
    private String admission;
    private String research;

    @Builder
    public Undergraduate(String name, String major, String email, String image, String dtype, String admission, String research, String office) {
        super(name, major, email, image, dtype, office);
        this.admission = admission;
        this.research = research;
    }

    public void update(String admission, String research) {
        this.admission = admission;
        this.research = research;
    }
}
