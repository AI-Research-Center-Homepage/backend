package byuntil.backend.post.service;


import byuntil.backend.common.exception.ApplicationException;
import byuntil.backend.common.exception.post.PostNotFoundException;
import byuntil.backend.common.exception.s3.UploadFailException;
import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.SourcePost;
import byuntil.backend.post.domain.repository.SourcePostRepository;
import byuntil.backend.post.dto.SourcePostDto;
import byuntil.backend.s3.domain.FileStatus;
import byuntil.backend.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SourcePostService {
    private final SourcePostRepository sourcePostRepository;

    private final S3Service s3Service;

    @Transactional
    public Long saveSource(SourcePostDto request, MultipartFile file) {
        SourcePost sourcePost = request.toEntity();
        Optional<FileStatus> fileStatusOptional = fileUpload(file);
        fileStatusOptional.ifPresent(fileStatus -> {
            String url = fileStatus.fileUrl();
            Attach build = Attach.builder()
                    .filePath(url)
                    .originFileName(file.getName())
                    .serverFileName(createStoreFilename(file.getName()))
                    .build();
            build.setSourcePost(sourcePost);
            sourcePost.addAttaches(build);
        });
        return sourcePostRepository.save(sourcePost).getId();
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
        String ext = extractExt(originalFilename);
        String storeFilename = uuid + ext;

        return storeFilename;
    }

    private String extractExt(String originalFilename) {
        int idx = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(idx);
        return ext;
    }

    /*public List<SourcePreviewMapping> findAllSourcePost() {
        return sourcePostRepository.findAllSource();
    }*/

    @Transactional
    public void sourceUpdate(final Long postId, final SourcePostDto request, final MultipartFile file) {
        Optional<SourcePost> postOptional = sourcePostRepository.findById(postId);
        postOptional.ifPresent(sourcePost -> {
            sourcePost.updatePost(request);
            fileUpload(file).ifPresent(fileStatus -> {
                //todo: 이게 표면적으로 지워지긴 하지만 s3에는 지워지지 않음 나중에 추가
                sourcePost.deleteAttaches();
                String url = fileStatus.fileUrl();
                Attach build = Attach.builder()
                        .filePath(url)
                        .originFileName(file.getName())
                        .serverFileName(createStoreFilename(file.getName()))
                        .build();
                build.setSourcePost(sourcePost);
                sourcePost.addAttaches(build);
            });
        });
    }

    @Transactional
    public void sourceDelete(final Long postId) {
        final SourcePost sourcePost = sourcePostRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        sourcePostRepository.delete(sourcePost);
    }

    @Transactional
    public int updateView(Long id) {
        return sourcePostRepository.updateView(id);
    }
}
