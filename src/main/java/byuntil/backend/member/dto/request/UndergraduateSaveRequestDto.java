package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controlller.domain.Login;
import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.entity.member.Undergraduate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class UndergraduateSaveRequestDto extends MemberSaveRequestDto {

    private LocalDateTime admission;

    @Builder
    public UndergraduateSaveRequestDto(Long id, String name, String major, String email, String image,
                                       LocalDateTime admission,String location, LoginDto loginDto) {
        super(id, name, major, email, image, location, loginDto);
        this.admission = admission;
    }

    @Override
    public Member toEntity() {
        //람다식을 사용하면 {} 안에서 지역변수 변경이 안돼서 if문 사용
        Login login = null;

        if (getLoginDto()!=null){
            login = getLoginDto().toEntity();
        }
        return Undergraduate.builder()
                .name(getName())
                .major(getMajor())
                .email(getEmail())
                .image(getImage())
                .admission(admission)
                .login(login)
                .location(getLocation())
                .build();
    }
}
