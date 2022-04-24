package byuntil.backend.post.service;


import byuntil.backend.post.domain.repository.NewsPostRepository;
import byuntil.backend.s3.service.S3Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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

    }
}