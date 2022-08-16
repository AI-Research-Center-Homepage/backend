package byuntil.backend.post.controller.common;

import byuntil.backend.post.domain.entity.Board;
import byuntil.backend.post.domain.entity.Post;
import byuntil.backend.post.dto.response.ArticleAndNewsResponseDto;
import byuntil.backend.post.dto.response.PostPreviewDto;
import byuntil.backend.post.dto.response.readPostDto;
import byuntil.backend.post.dto.response.PostResponseDto;
import byuntil.backend.post.service.BoardService;
import byuntil.backend.post.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final BoardService boardService;
    private final PostService postService;

    //미리보기
    @GetMapping("/article")
    public ResponseEntity<PostResponseDto> readPreviewArticle(Pageable pageable) {
        Board article = boardService.findByName("Article");
        List<Post> posts = article.getPosts();
        postService.findAllForAdmin("Article", )
        return getPostResponseDtoResponseEntity(posts, article.getName());
    }
    //news
    @GetMapping("/news")
    public ResponseEntity readPreviewNews(Pageable pageable) {
        Board news = boardService.findByName("News");
        List<Post> posts = news.getPosts();
        return getPostResponseDtoResponseEntity(posts, news.getName());
    }

    //notice
    @GetMapping("/notice")
    public ResponseEntity<PostResponseDto> readPreviewNotice(Pageable pageable) {
        Board notice = boardService.findByName("Notice");
        List<Post> posts = notice.getPosts();
        return getPostResponseDtoResponseEntity(posts, notice.getName());
    }


    @GetMapping("/article/{postId}")
    public ResponseEntity<ArticleAndNewsResponseDto> readEachArticle(@PathVariable("postId") final Long postId) {
        postService.findById(postId, "Article").ifPresent(
                post -> {postService.updateView(postId);}
        );

        return getArticleAndNewsResponseDtoResponseEntity(postId);
    }


    @GetMapping("/news/{postId}")
    public ResponseEntity<ArticleAndNewsResponseDto> readEachNews(@PathVariable("postId") final Long postId) {
        postService.findById(postId, "News").ifPresent(
                post -> {postService.updateView(postId);}
        );
        return getArticleAndNewsResponseDtoResponseEntity(postId);
    }

    @GetMapping("/notice/{postId}")
    public ResponseEntity<readPostDto> readEachNotice(@PathVariable("postId") final Long postId) {
        readPostDto response = postService.readNotice(postId);
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
                .viewNum(post.getViewNum())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate()).build();
        return ResponseEntity.ok().body(response);
    }

}
