package byuntil.backend.post.service;

import byuntil.backend.post.domain.entity.Post;
import byuntil.backend.post.domain.repository.PostRepository;
import byuntil.backend.post.dto.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static byuntil.backend.common.factory.MockPostFactory.createMockPostDto;

@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService service;
    @Autowired
    private PostRepository repository;

    @Test
    void findAllNewsPost() {
        //given
        PostDto request = createMockPostDto("title2", "author2", "content2");
        service.saveNews(request, null);
        //when
        List<Post> allNews = service.findAllNews();
        //List<NewsAndNoticePreviewMapping> newsPostById = repository.findNewById(1L);
        //then
        System.out.println("allNews.get(0) = " + allNews.get(0));
        System.out.println("allNews.get(0).getCreatedDate() = " + allNews.get(0).getCreatedDate());
        //System.out.println("newsPostById.get(0).getTitle() = " + newsPostById.get(0).getTitle());
    }
}