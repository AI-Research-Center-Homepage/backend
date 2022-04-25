package byuntil.backend.post.service;


import byuntil.backend.post.domain.entity.NewsPost;
import byuntil.backend.post.domain.repository.NewsPostRepository;
import byuntil.backend.post.dto.NewsPostDto;
import byuntil.backend.s3.domain.FileStatus;
import byuntil.backend.s3.domain.FileType;
import byuntil.backend.s3.service.S3Service;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static byuntil.backend.common.factory.MockFileFactory.createMultipartFileText;
import static byuntil.backend.common.factory.MockPostFactory.createMockNewsPostDto;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class NewsPostServiceTest {
    @Mock
    private NewsPostRepository repository;

    @Mock
    private S3Service s3Service;
    @InjectMocks
    private NewsPostService service;

    @DisplayName("saveNews메소드는")
    @Nested
    class SaveNewsMethod {
        @DisplayName("News 게시글을 생성할 수 있다. 파일 첨부 O")
        @Test
        void createNewPostWithFile_success() throws IOException {
            //given
            final NewsPost post = mock(NewsPost.class);
            final NewsPostDto postDto = createMockNewsPostDto("title2", "author2", "content2");
            final MockMultipartFile textFile = createMultipartFileText();
            Optional<FileStatus> fileStatus = Optional.of(new FileStatus("url", FileType.FILE));

            given(repository.save(post))
                    .willReturn(post);
            given(post.getId())
                    .willReturn(1L);
            given(s3Service.uploadPostFile(textFile))
                    .willReturn(fileStatus);
            //when
            Long postId = service.saveNews(postDto, textFile);

            //then
            verify(repository, times(1))
                    .save(any(NewsPost.class));
            verify(s3Service, times(1))
                    .uploadPostFile(any(MultipartFile.class));
            Assertions.assertThat(postId).isEqualTo(1L);
        }
    }
}