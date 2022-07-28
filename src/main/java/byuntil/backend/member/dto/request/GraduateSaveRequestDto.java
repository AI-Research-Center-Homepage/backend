package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controlller.domain.Login;
import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Graduate;
import byuntil.backend.member.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GraduateSaveRequestDto extends MemberSaveRequestDto {

    private LocalDateTime admission;

    @Builder
    public GraduateSaveRequestDto(Long id, String name, String major, String email, String image,
                                  LocalDateTime admission, String location, LoginDto loginDto) {
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
