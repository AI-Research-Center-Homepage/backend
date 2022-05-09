package byuntil.backend.post.service;


import byuntil.backend.common.S3MockConfig;
import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.Post;
import byuntil.backend.post.domain.repository.PostRepository;
import byuntil.backend.post.dto.PostDto;
import byuntil.backend.s3.domain.FileStatus;
import byuntil.backend.s3.domain.FileType;
import byuntil.backend.s3.service.S3Service;
import io.findify.s3mock.S3Mock;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static byuntil.backend.common.factory.MockPostFactory.createMockNewsPostDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PostServiceMockTest {
    @Mock
    private PostRepository repository;

    @Mock
    private S3Service s3Service;
    @InjectMocks
    private PostService service;

    @Autowired
    S3Mock s3Mock;

    @After("")
    public void shutdownMockS3() {
        s3Mock.stop();
    }

    @DisplayName("saveNews메소드는")
    @Nested
    @Import(S3MockConfig.class)
    class SaveNewsMethod {
        @DisplayName("News 게시글을 생성할 수 있다. 파일 첨부 O")
        @Test
        void createNewPostWithFile_success() throws IOException {
            //given
            final Post post = mock(Post.class);
            final PostDto postDto = createMockNewsPostDto("title2", "author2", "content2");
            final MockMultipartFile textFile = new MockMultipartFile("file", "testText.txt", "text/plain", "test data".getBytes());
            Optional<FileStatus> fileStatus = Optional.of(new FileStatus("url", FileType.FILE));

            when(repository.save(any(Post.class))).thenReturn(post);
            when(post.getId()).thenReturn(1L);
            when(s3Service.uploadPostFile(textFile)).thenReturn(fileStatus);
            //when
            final Long postId = service.saveNews(postDto, textFile);

            //then
            verify(repository, times(1))
                    .save(any(Post.class));
            verify(s3Service, times(1))
                    .uploadPostFile(any(MultipartFile.class));
            assertThat(postId).isEqualTo(1L);
        }

        @DisplayName("News 게시글을 생성할 수 있다. 파일 첨부 X")
        @Test
        void createNewPostWihtNoFile_success() throws IOException {
            //given
            final Post post = mock(Post.class);
            final PostDto postDto = createMockNewsPostDto("title2", "author2", "content2");
            final MockMultipartFile textFile = new MockMultipartFile("file", null, null, new byte[0]);

            when(repository.save(any(Post.class))).thenReturn(post);
            when(post.getId()).thenReturn(1L);
            //when
            final Long postId = service.saveNews(postDto, textFile);

            //then
            verify(repository, times(1))
                    .save(any(Post.class));
            verify(s3Service, times(0))
                    .uploadPostFile(any());
            assertThat(postId).isEqualTo(1L);
        }
    }

    @DisplayName("update 메서드는")
    @Nested
    class UpdateMethod {
        @DisplayName("post 게시글을 수정할 수 있다. 파일 첨부 O")
        @Test
        void updateNewsPostWihtFile_success() throws IOException {
            //given
            final Post post = mock(Post.class);
            final PostDto postDto = createMockNewsPostDto("title2", "author2", "content2");
            final MockMultipartFile textFile = new MockMultipartFile("file", "testText.txt", "text/plain", "test data".getBytes());
            Optional<FileStatus> fileStatus = Optional.of(new FileStatus("url", FileType.FILE));

            when(post.getId()).thenReturn(1L);
            when(s3Service.uploadPostFile(textFile)).thenReturn(fileStatus);
            when(repository.findById(any())).thenReturn(Optional.of(post));
            //when
            service.updateNews(post.getId(), postDto, textFile);
            //then
            verify(repository, times(1))
                    .findById(anyLong());
            verify(post, times(1))
                    .updatePost(any(PostDto.class));
            verify(s3Service, times(1))
                    .uploadPostFile(any(MultipartFile.class));
            verify(post, times(1))
                    .deleteAttaches();
            verify(post, times(1))
                    .addAttaches(any(Attach.class));
        }
    }

    @DisplayName("deleteNews 메소드는")
    @Nested
    class DeleteMethod {
        @DisplayName("post 게시글을 지울수 있다.")
        @Test
        void deleteNewsPost() {
            //given
            final Post post = mock(Post.class);
            when(repository.findById(anyLong()))
                    .thenReturn(Optional.of(post));

            //when
            service.deleteNews(1L);

            //then
            verify(repository, times(1))
                    .findById(anyLong());
            verify(repository, times(1))
                    .delete(any(Post.class));
        }
    }

    @DisplayName("updateView 메소드는")
    void updateView() {
        //given
    }
}