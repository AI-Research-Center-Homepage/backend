package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controlller.domain.Login;
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
    public Committee(String name, String major, String email, String image, Login login,
                     String dtype, String position, String location) {
        super(name, major, email, image, dtype, location, login);
        this.position = position;
    }

    public void update(String position) {
        this.position = position;
    }
}
