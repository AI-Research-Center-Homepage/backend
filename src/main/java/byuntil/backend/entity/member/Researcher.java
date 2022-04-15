package byuntil.backend.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Researcher extends Member {
    private String research;

    @Builder
    public Researcher(Long id, String name, String major, String email, String image, String research) {
        super(id, name, major, email, image);
        this.research = research;
    }
}
