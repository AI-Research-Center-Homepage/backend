package byuntil.backend.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class readAllPostDto {
    List<readPostDto> list;
}
