package byuntil.backend.post.service;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.repository.AttachRepository;
import byuntil.backend.post.dto.AttachDto;
import byuntil.backend.response.DefaultRes;
import byuntil.backend.response.ResponseMessage;
import byuntil.backend.response.StatusCode;
import byuntil.backend.s3.service.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachService {
    private final S3ServiceImpl s3Service;
    private final AttachRepository attachRepository;

    @Transactional
    public DefaultRes save(final AttachDto dto, final List<MultipartFile> multipartFileList) {
        try {
            if (!multipartFileList.isEmpty()) {
                //upload후에 dto에 적절한 serverfilename이 들어가게 된다
                s3Service.uploads(multipartFileList);
            }
            //적절한 값이 들어가게 된 dto를 entity로 변환하여 db에 저장한다
            Attach attach = attachRepository.save(dto.toEntity());
            return DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_PROFILE_REGISTER);
        } catch (Exception e) {
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

}
