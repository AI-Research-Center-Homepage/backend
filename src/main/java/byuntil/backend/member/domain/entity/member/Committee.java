package byuntil.backend.member.domain.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Committee extends Member {
    private String position;

    @Builder
    public Committee(Long id, String name, String major, String email, String image, String dtype, String position) {
        super(id, name, major, email, image, dtype);
        this.position = position;
    }

    public void update(String position) {
        this.position = position;
    }
}
