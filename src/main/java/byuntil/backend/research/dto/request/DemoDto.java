package byuntil.backend.research.dto.request;

import byuntil.backend.research.domain.entity.Demo;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
public class DemoDto {
    private Long id;
    private final String title;
    private final String description;
    private final String content;
    private final String url;
    private final String participants;


    public Demo toEntity() {
        return Demo.builder().title(title).content(content).url(url).participants(participants).description(description).build();
    }
}
