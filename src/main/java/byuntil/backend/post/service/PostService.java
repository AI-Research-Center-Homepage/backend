package byuntil.backend.post.service;

import byuntil.backend.common.exception.ApplicationException;
import byuntil.backend.common.exception.post.PostNotFoundException;
import byuntil.backend.common.exception.s3.UploadFailException;
import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.Post;
import byuntil.backend.post.domain.repository.PostRepository;
import byuntil.backend.post.dto.PostDto;
import byuntil.backend.s3.domain.FileStatus;
import byuntil.backend.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository newsPostRepository;
    private final S3Service s3Service;

    @Transactional
    public Long saveNews(PostDto request, MultipartFile file) {
        Post newsPost = request.toEntity();
        Optional<FileStatus> fileStatusOptional = fileUpload(file);
        fileStatusOptional.ifPresent(fileStatus -> {
            String url = fileStatus.fileUrl();
            Attach build = Attach.builder()
                    .filePath(url)
                    .originFileName(file.getName())
                    .serverFileName(createStoreFilename(file.getName()))
                    .build();
            build.setPost(newsPost);
            newsPost.addAttaches(build);
        });
        return newsPostRepository.save(newsPost).getId();
    }

    private Optional<FileStatus> fileUpload(MultipartFile file) {
        if (Objects.isNull(file)) {
            return Optional.empty();
        } else if (file.isEmpty()) {
            return Optional.empty();
        }

        try {
            return s3Service.uploadPostFile(file);
        } catch (ApplicationException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UploadFailException();
        }
    }

    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        /*String ext = extractExt(originalFilename);
        String storeFilename = uuid + ext;*/
        String storeFilename = uuid + originalFilename;

        return storeFilename;
    }

    private String extractExt(String originalFilename) {
        int idx = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(idx);
        return ext;
    }

    /*public List<NewsAndNoticePreviewMapping> findAllNewsPost() {
        return newsPostRepository.findAllNews();
    }*/

    public List<Post> findAllNews() {
        return newsPostRepository.findAll();
    }

    @Transactional
    public void updateNews(final Long postId, final PostDto request, final MultipartFile file) {
        Optional<Post> postOptional = newsPostRepository.findById(postId);
        postOptional.ifPresent(newsPost -> {
            newsPost.updatePost(request);
            fileUpload(file).ifPresent(fileStatus -> {
                //todo: 이게 표면적으로 지워지긴 하지만 s3에는 지워지지 않음 나중에 추가
                newsPost.deleteAttaches();
                String url = fileStatus.fileUrl();
                Attach build = Attach.builder()
                        .filePath(url)
                        .originFileName(file.getName())
                        .serverFileName(createStoreFilename(file.getName()))
                        .build();
                build.setPost(newsPost);
                newsPost.addAttaches(build);
            });
        });
    }

    @Transactional
    public void deleteNews(final Long postId) {
        final Post newsPost = newsPostRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        newsPostRepository.delete(newsPost);
    }

    @Transactional
    public int updateView(Long id) {
        return newsPostRepository.updateView(id);
    }
}
