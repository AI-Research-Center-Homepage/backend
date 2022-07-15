package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controlller.domain.Login;
import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.dto.request.UndergraduateSaveRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Undergraduate extends Member {
    private LocalDateTime admission;

    @Builder
    public Undergraduate(final String name, final String major, final String email, final String image, final String dtype,
                         LocalDateTime admission, String location, Login login) {
        super(name, major, email, image, dtype, location, login);
        this.admission = admission;
    }

    public void update(final LocalDateTime admission) {
        this.admission = admission;
    }

    public UndergraduateSaveRequestDto toDto(){
        LoginDto loginDto = new LoginDto(getLogin().getLoginId(), getLogin().getLoginPw());
        return UndergraduateSaveRequestDto.builder().email(getEmail()).loginDto(loginDto).location(getLocation()).image(getImage()).admission(getAdmission())
                .name(getName()).major(getMajor()).build();
    }
}
