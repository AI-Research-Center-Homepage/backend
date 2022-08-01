package byuntil.backend.post.controller.common;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.Board;
import byuntil.backend.post.domain.entity.Post;
import byuntil.backend.post.dto.response.ArticleAndNewsResponseDto;
import byuntil.backend.post.dto.response.AttachResponseDto;
import byuntil.backend.post.dto.response.readPostDto;
import byuntil.backend.post.dto.response.PostResponseDto;
import byuntil.backend.post.service.BoardService;
import byuntil.backend.post.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final BoardService boardService;
    private final PostService postService;

    //article
    @GetMapping("/article ")
    public ResponseEntity<PostResponseDto<PostPreviewDto>> readPreviewArticle() {
        Board article = boardService.findByName("Article");
        List<Post> posts = article.getPosts();
        return getPostResponseDtoResponseEntity(posts, article.getName());
    }

    @GetMapping("/article/{postId}")
    public ResponseEntity<ArticleAndNewsResponseDto> readEachArticle(@PathVariable("postId") final Long postId) {
        return getArticleAndNewsResponseDtoResponseEntity(postId);
    }

    //news
    @GetMapping("/news")
    public ResponseEntity<PostResponseDto<PostPreviewDto>> readPreviewNews() {
        Board news = boardService.findByName("News");
        List<Post> posts = news.getPosts();
        return getPostResponseDtoResponseEntity(posts, news.getName());
    }

    @GetMapping("/news/{postId}")
    public ResponseEntity<ArticleAndNewsResponseDto> readEachNews(@PathVariable("postId") final Long postId) {
        return getArticleAndNewsResponseDtoResponseEntity(postId);
    }

    //notice
    @GetMapping("/notice")
    public ResponseEntity<PostResponseDto<PostPreviewDto>> readPreviewNotice() {
        Board notice = boardService.findByName("Notice");
        List<Post> posts = notice.getPosts();
        return getPostResponseDtoResponseEntity(posts, notice.getName());
    }

    @GetMapping("/notice/{postId}")
    public ResponseEntity<readPostDto> readEachNotice(@PathVariable("postId") final Long postId) {
        Post post = postService.findById(postId);
        List<Attach> attaches = post.getAttaches();
        List<AttachResponseDto> attachResponseDtos = attaches.stream().map(AttachResponseDto::new).toList();
        readPostDto response = readPostDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .viewNum(post.getViewNum())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
        return ResponseEntity.ok().body(response);
    }

    private ResponseEntity<PostResponseDto<PostPreviewDto>> getPostResponseDtoResponseEntity(final List<Post> posts, final String boardName) {
        List<PostPreviewDto> postPreviewDtos = posts.stream().map(PostPreviewDto::new).toList();
        PostResponseDto<PostPreviewDto> response = PostResponseDto.<PostPreviewDto>builder().posts(postPreviewDtos).boardName(boardName).build();
        return ResponseEntity.ok().body(response);
    }

    private ResponseEntity<ArticleAndNewsResponseDto> getArticleAndNewsResponseDtoResponseEntity(final Long postId) {
        Post post = postService.findById(postId);
        ArticleAndNewsResponseDto response = ArticleAndNewsResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate()).build();
        return ResponseEntity.ok().body(response);
    }

    @Getter
    static class PostPreviewDto {
        private final Long id;
        private final String title;
        private final List<String> images;
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private final LocalDateTime createdDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private final LocalDateTime modifiedDate;

        public PostPreviewDto(final Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.images = post.getImageList();
            this.createdDate = post.getCreatedDate();
            this.modifiedDate = post.getModifiedDate();
        }
    }
}
