package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controlller.domain.Login;
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

    @Builder
    public Professor(final String name, final String major, final String email, final String image, final String dtype,
                     final String doctorate, final String location, final String number, final Login login) {
        super(name, major, email, image, dtype, location, login);
        this.doctorate = doctorate;
        this.number = number;
    }

    public void update(final String doctorate, final String number) {
        this.doctorate = doctorate;
        this.number = number;
    }
}
