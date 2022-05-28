package byuntil.backend.research.dto;

import byuntil.backend.research.domain.entity.Demo;
import lombok.Data;

@Data
public class DemoDto {
    private Long id;
    private String name;
    private String content;
    private String url;

    public Demo toEntity(){
        return Demo.builder().name(name).content(content).url(url).build();
    }
}
