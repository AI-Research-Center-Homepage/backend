package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AttachDto {
    //외부에서 데이터가 들어올때는 originalfilename만 들어올테니까 그거만 받는 dto를 만들고
    //나머지값들은 그냥 attach를 만든다음에 주입해주는걸로..
    private Long id;
    private String originFileName;
    @Builder
    public AttachDto(String originFileName, String serverFileName, String filePath, Post post) {
        this.originFileName = originFileName;
    }

    public Attach toEntity() {
        Attach build = Attach.builder()
                .originFileName(originFileName)
                .build();
        return build;
    }
}
