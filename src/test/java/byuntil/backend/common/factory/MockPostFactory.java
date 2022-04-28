package byuntil.backend.common.factory;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.NewsPost;
import byuntil.backend.post.domain.entity.NoticePost;
import byuntil.backend.post.domain.entity.SourcePost;
import byuntil.backend.post.dto.NewsPostDto;
import byuntil.backend.post.dto.NoticePostDto;
import byuntil.backend.post.dto.SourcePostDto;

public class MockPostFactory {
    public static NewsPost createMockNewsPost() {
        return NewsPost.builder()
                .title("title")
                .author("author")
                .content("newsContent")
                .viewNum(1)
                .build();
    }

    public static NoticePost createMockNoticePost() {
        return NoticePost.builder()
                .title("title")
                .author("author")
                .content("noticeContent")
                .viewNum(1)
                .build();
    }

    public static SourcePost createMockSourcePost() {
        return SourcePost.builder()
                .title("title")
                .author("author")
                .content("sourceContent")
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

    public static NewsPostDto createMockNewsPostDto(String title, String author, String content) {
        return NewsPostDto.builder()
                .id(1L)
                .title(title)
                .author(author)
                .content(content)
                .build();
    }

    public static NoticePostDto createMockNoticePostDto(String title, String author, String content) {
        return NoticePostDto.builder()
                .id(1L)
                .title(title)
                .author(author)
                .content(content)
                .build();
    }

    public static SourcePostDto createMockSourcePostDto(String title, String author, String content) {
        return SourcePostDto.builder()
                .id(1L)
                .title(title)
                .author(author)
                .content(content)
                .build();
    }

}
