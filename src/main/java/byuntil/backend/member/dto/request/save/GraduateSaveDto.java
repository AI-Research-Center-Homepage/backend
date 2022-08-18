package byuntil.backend.member.dto.request.save;

import byuntil.backend.admin.controller.domain.Login;
import byuntil.backend.admin.controller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Graduate;
import byuntil.backend.member.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GraduateSaveDto extends MemberSaveDto {

    private int admission;

    @Builder
    public GraduateSaveDto(Long id, String name, String major, String email, String image,
                           int admission, String location, LoginDto loginDto) {
        super(id, name, major, email, image, location, loginDto);
        this.admission = admission;
    }


    @Override
    public Member toEntity() {
        Login login = null;

        if (getLoginDto()!=null){
            login = getLoginDto().toEntity();
        }

        return Graduate.builder()
                .name(getName())
                .major(getMajor())
                .email(getEmail())
                .image(getImage())
                .admission(admission)
                .location(getLocation())
                .login(login)
                .build();
    }
}
