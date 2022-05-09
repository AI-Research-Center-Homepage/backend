package byuntil.backend.common.factory;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.Post;
import byuntil.backend.post.dto.PostDto;

public class MockPostFactory {
    public static Post createMockNewsPost() {
        return Post.builder()
                .title("title")
                .author("author")
                .content("newsContent")
                .viewNum(1)
                .build();
    }

    public static Attach createMockAttach() {
        return Attach.builder()
                .filePath("/path")
                .originFileName("orginal.txt")
                .serverFileName("orginal.txt1234")
                .build();
    }

    public static PostDto createMockNewsPostDto(String title, String author, String content) {
        return PostDto.builder()
                .id(1L)
                .title(title)
                .author(author)
                .content(content)
                .build();
    }

}
