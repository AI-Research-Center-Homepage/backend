package byuntil.backend.post.service;

import byuntil.backend.post.domain.entity.NewsPost;
import byuntil.backend.post.domain.repository.NewsPostRepository;
import byuntil.backend.post.dto.NewsPostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static byuntil.backend.common.factory.MockPostFactory.createMockNewsPostDto;

@SpringBootTest
class NewsPostServiceTest {
    @Autowired
    private NewsPostService service;
    @Autowired
    private NewsPostRepository repository;

    @Test
    void findAllNewsPost() {
        //given
        NewsPostDto request = createMockNewsPostDto("title2", "author2", "content2");
        service.saveNews(request, null);
        //when
        List<NewsPost> allNews = service.findAllNews();
        //List<NewsAndNoticePreviewMapping> newsPostById = repository.findNewById(1L);
        //then
        System.out.println("allNews.get(0) = " + allNews.get(0));
        System.out.println("allNews.get(0).getCreatedDate() = " + allNews.get(0).getCreatedDate());
        //System.out.println("newsPostById.get(0).getTitle() = " + newsPostById.get(0).getTitle());
    }
}