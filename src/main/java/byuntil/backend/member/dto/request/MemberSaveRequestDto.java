package byuntil.backend.member.dto.request;

import byuntil.backend.admin.domain.Login;
import byuntil.backend.admin.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class MemberSaveRequestDto {
    private String name;
    private String major;
    private String email;
    private String image;
    private String office;
    private String fields;
    private LoginDto loginDto;

    abstract public Member toEntity();

}
