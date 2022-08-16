package byuntil.backend.post.dto.response;

import byuntil.backend.post.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Builder
public class PostPreviewDto {
    private final Long id;
    private final String title;
    private final Integer viewNum;
    private String image;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime modifiedDate;

    public PostPreviewDto(final Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.viewNum = post.getViewNum();
        Optional.ofNullable(post.getImageList()).ifPresent(
                imageList -> {
                    if (post.getImageList().size() >= 1) this.image = post.getImageList().get(0);
                }
        );
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
    }
}
