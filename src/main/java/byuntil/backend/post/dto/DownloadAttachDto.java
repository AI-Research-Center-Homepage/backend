package byuntil.backend.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DownloadAttachDto {
    private Long id;
    private String fileName;//이 filename으로 다운로드받을거라는얘기 -> 맨처음에 파일을 업로드한 그 이름으로 해야겠다
    private String fileKey;
    private Long postId;
    //db에서
    @Builder
    public DownloadAttachDto(String fileKey, String fileName){
        this.fileKey = fileKey;
        this.fileName = fileName;
    }
}
