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
    public Undergraduate(Long id, String name, String major, String email, String image, String dtype, String admission, String research) {
        super(id, name, major, email, image, dtype);
        this.admission = admission;
        this.research = research;
    }

    public void update(String admission, String research) {
        this.admission = admission;
        this.research = research;
    }
}
