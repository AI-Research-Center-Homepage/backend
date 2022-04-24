package byuntil.backend.s3.service;

import byuntil.backend.s3.domain.FileStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface S3Service {
    Optional<String> uploadUserImage(final MultipartFile image) throws IOException;

    Optional<FileStatus> uploadPostFile(final MultipartFile file) throws IOException;
}
