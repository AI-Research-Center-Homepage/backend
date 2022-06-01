package byuntil.backend.post.domain.repository;

import byuntil.backend.post.domain.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static byuntil.backend.common.factory.MockPostFactory.createMockPost;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository newsPostRepository;
    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("save 메소드는")
    @Nested
    class SaveMethod {
        @DisplayName("게시글을 등록할수 있다.")
        @Test
        void saveNewsPost_success() {
            //given
            LocalDateTime now = LocalDateTime.now();
            final Post newsPost = Post.builder()
                    .title("title")
                    .content("content")
                    .viewNum(1)
                    .build();
            //when
            newsPostRepository.save(newsPost);
            //then
            final List<Post> posts = newsPostRepository.findAll();
            assertAll("게시글 등록 테스트",
                    () -> assertThat(posts.size()).isEqualTo(1),
                    () -> assertThat(posts.get(0).getTitle()).isEqualTo(newsPost.getTitle()),
                    () -> assertThat(posts.get(0).getContent()).isEqualTo(newsPost.getContent())
            );
            System.out.println("======CreateDate : " + posts.get(0).getCreatedDate() + "=====ModifiedDate : " + posts.get(0).getModifiedDate());
            assertThat(posts.get(0).getCreatedDate()).isAfter(now);
            assertThat(posts.get(0).getModifiedDate()).isAfter(now);
        }

    }

    @DisplayName("findById 메소드는")
    @Nested
    class FindByIdMethod {
        @DisplayName("postId로 조회할 수 있다.")
        @Test
        void findByIdNewsPost_success() {
            //given
            final Post newsPost = createMockPost();
            final Long postId = testEntityManager.persist(newsPost).getId();
            //when
            final Optional<Post> findedPost = newsPostRepository.findById(postId);
            //then
            assertAll(
                    () -> assertThat(findedPost).isNotEmpty(),
                    () -> assertThat(findedPost.get().getContent()).isEqualTo(newsPost.getContent()),
                    () -> assertThat(findedPost.get().getTitle()).isEqualTo(newsPost.getTitle())
            );
        }

        @DisplayName("존재하지 않는 Id로 조회할 수 없다.")
        @Test
        void findByIdNewsPost_fail() {
            //when
            final Optional<Post> post = newsPostRepository.findById(1L);

            //then
            assertThat(post).isEmpty();
        }
    }

    @DisplayName("delete 메소드는")
    @Nested
    class DeleteMethod {
        @DisplayName("게시글을 지울수 있다.")
        @Test
        void deleteNewsPost_success() {
            //given
            final Post newsPost = createMockPost();
            testEntityManager.persist(newsPost);

            assertThat(newsPostRepository.findAll().size()).isEqualTo(1);
            //when
            newsPostRepository.delete(newsPost);
            testEntityManager.flush();
            testEntityManager.clear();

            //then
            assertThat(newsPostRepository.findAll().size()).isEqualTo(0);
        }
    }
}