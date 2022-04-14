package byuntil.backend.entity.member;

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
    public Committee(String position) {
        this.position = position;
    }
}
