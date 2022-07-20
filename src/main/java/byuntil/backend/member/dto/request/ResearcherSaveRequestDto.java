package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controlller.domain.Login;
import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.entity.member.Researcher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResearcherSaveRequestDto extends MemberSaveRequestDto {

    @Builder
    public ResearcherSaveRequestDto(String name, String major, String email, String image, String location, LoginDto loginDto) {
        super(name, major, email, image, location, loginDto);
    }


    @Override
    public Member toEntity() {
        Login login = null;

        if (getLoginDto()!=null){
            login = getLoginDto().toEntity();
        }
        return Researcher.builder()
                .name(getName())
                .major(getMajor())
                .email(getEmail())
                .image(getImage())
                .location(getLocation())
                .login(login)
                .build();
    }
}
