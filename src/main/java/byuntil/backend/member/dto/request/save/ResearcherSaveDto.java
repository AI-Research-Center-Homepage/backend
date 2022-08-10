package byuntil.backend.member.dto.request.save;

import byuntil.backend.admin.controller.domain.Login;
import byuntil.backend.admin.controller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.entity.member.Researcher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResearcherSaveDto extends MemberSaveDto {

    @Builder
    public ResearcherSaveDto(Long id, String name, String major, String email, String image, String location, LoginDto loginDto) {
        super(id, name, major, email, image, location, loginDto);
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
