package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class MemberSaveRequestDto {
    private Long id;
    private String name;
    private String major;
    private String email;
    private String image;
    private String location;
    private LoginDto loginDto;

    abstract public Member toEntity();

}
