package byuntil.backend.member.dto.response.one;

import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.Builder;

import java.time.LocalDateTime;

public class OneProfessorResponseDto extends OneMemberResponseDto{
    private String doctorate;
    private String number;
    @Builder
    public OneProfessorResponseDto(Long id, String name, String major, String email,
                                  String image, String doctorate, String number, String location, LoginResponseDto loginDto) {
        super(id, name, major, email, image, location, loginDto);
        this.doctorate = doctorate;
        this.number = number;
    }
}
