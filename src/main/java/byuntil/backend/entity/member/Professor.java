package byuntil.backend.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor extends Member {
    private String doctorate;
    private String location;
    private String number;

    @Builder
    public Professor(Long id, String name, String major, String email, String image, String doctorate, String location, String number) {
        super(id, name, major, email, image);
        this.doctorate = doctorate;
        this.location = location;
        this.number = number;
    }
}
