package byuntil.backend.member.dto.request;

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

    abstract public Member toEntity();
}
