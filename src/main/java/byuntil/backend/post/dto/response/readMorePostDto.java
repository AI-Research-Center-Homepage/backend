package byuntil.backend.post.dto.response;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.Post;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class readMorePostDto {
    private final String title;
    private final String content;
    private final String author;
    private final int viewNum;
    private final List<AttachResponseDto> attaches;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss:")
    private final LocalDateTime createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss:")
    private final LocalDateTime modifiedDate;

    public readMorePostDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.viewNum = post.getViewNum();
        attaches = new ArrayList<>();
        if (Optional.ofNullable(post.getAttaches()).isPresent()){
            for (Attach attach: post.getAttaches()) {
                AttachResponseDto dto = new AttachResponseDto(attach);
                attaches.add(dto);
            }
        }
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
    }
}
