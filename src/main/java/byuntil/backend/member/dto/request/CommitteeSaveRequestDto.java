package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controlller.domain.Login;
import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Committee;
import byuntil.backend.member.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommitteeSaveRequestDto extends MemberSaveRequestDto {
    private String position;

    @Builder
    public CommitteeSaveRequestDto(Long id, String name, String major, String email,
                                   String image, String position, String location, LoginDto loginDto) {
        super(id, name, major, email, image, location, loginDto);
        this.position = position;
    }


    @Override
    public Member toEntity() {
        Login login = null;

        if (getLoginDto()!=null){
            login = getLoginDto().toEntity();
        }
        return Committee.builder()
                .name(getName())
                .email(getEmail())
                .image(getImage())
                .major(getMajor())
                .position(getPosition())
                .location(getLocation())
                .login(login)
                .build();
    }
}
