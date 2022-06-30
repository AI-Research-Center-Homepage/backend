package byuntil.backend.research.dto;

import byuntil.backend.research.domain.entity.Demo;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class DemoDto {
    private final Long id;
    private final String title;
    private final String description;
    private final String content;
    private final String url;
    private final String participants;

    public DemoDto(final Demo demo) {
        this.id = demo.getId();
        this.title = demo.getTitle();
        this.description = demo.getDescription();
        this.content = demo.getContent();
        this.url = demo.getUrl();
        this.participants = demo.getParticipants();
    }

    public Demo toEntity() {
        return Demo.builder().title(title).content(content).url(url).participants(participants).build();
    }
}
