package byuntil.backend.s3.service;

import byuntil.backend.common.exception.s3.InvalidExtensionException;
import byuntil.backend.common.exception.s3.InvalidFileNameException;
import byuntil.backend.common.exception.s3.UploadFailException;
import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.dto.AttachDto;
import byuntil.backend.s3.domain.*;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


@Service
@PropertySource("classpath:application-credentials.properties")
@PropertySource("classpath:application-aws.properties")
@Slf4j
public class S3ServiceImpl implements S3Service {

    private static final List<String> PERMISSION_IMG_EXTENSIONS = List.of("png", "jpg", "jpeg", "gif", "tif", "ico", "svg", "bmp", "webp", "tiff", "jfif");
    private static final List<String> PERMISSION_FILE_EXTENSIONS = List.of("doc", "docx", "xls", "xlsx", "hwp", "pdf", "txt", "md", "ppt", "pptx", "key");

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;

    private AmazonS3 s3Client;

    @PostConstruct
    private void setS3Client() {
        final AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    //Image
    @Transactional
    public Optional<String> uploadUserImage(MultipartFile image) throws IOException {
        return image.isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(uploadImg(image));
    }

    private String uploadImg(final MultipartFile file) throws IOException {
        final String fileName = getFileName(file);
        final String extension = getFileExtension(fileName);
        validateImageExtension(extension);

        return upload(file, fileName);
    }

    private String getFileName(final MultipartFile file) {
        final String fileName = file.getOriginalFilename();
        if (isEmpty(fileName)) {
            throw new InvalidFileNameException();
        }
        return fileName;
    }

    private String getFileExtension(String fileName) {
        final int index = fileName.lastIndexOf(".");
        if (index > 0 && fileName.length() > index + 1) {
            return fileName.substring(index + 1);
        } else {
            throw new InvalidExtensionException();
        }
    }

    private void validateImageExtension(final String extension) {
        if (!PERMISSION_IMG_EXTENSIONS.contains(extension)) {
            throw new InvalidExtensionException();
        }
    }

    //File
    @Transactional
    public Optional<FileStatus> uploadPostFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return Optional.empty();
        }

        String fileUrl = uploadFile(file);
        if (isNotEmpty(fileUrl)) {
            return Optional.of(new FileStatus(fileUrl, FileType.FILE));
        }
        fileUrl = uploadImg(file);
        if (isNotEmpty(fileUrl)) {
            return Optional.of(new FileStatus(fileUrl, FileType.IMAGE));
        }

        throw new UploadFailException();
    }

    private String uploadFile(final MultipartFile file) throws IOException {
        final String fileName = getFileName(file);
        final String extension = getFileExtension(fileName);

        if (validateFileExtension(extension)) {
            return upload(file, fileName);
        } else {
            return null;
        }
    }

    private boolean validateFileExtension(final String extension) {
        return PERMISSION_FILE_EXTENSIONS.contains(extension);
    }

    private String upload(final MultipartFile file, final String fileName) throws IOException {
        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return String.valueOf(s3Client.getUrl(bucket, fileName));
    }

    //minji

    public String upload(MultipartFile uploadFile) throws IOException {
        List<Attach> attachList = new ArrayList<>();
        String resultUrl;
            String origName = uploadFile.getOriginalFilename();
            String url;
            try {
                // 확장자를 찾기 위한 코드
                final String ext = origName.substring(origName.lastIndexOf('.'));
                // 파일이름 암호화
                final String saveFileName = getUuid() + ext;
                // 파일 객체 생성
                // System.getProperty => 시스템 환경에 관한 정보를 얻을 수 있다. (user.dir = 현재 작업 디렉토리를 의미함)
                File file = new File(System.getProperty("user.dir") + saveFileName);
                // 파일 변환
                uploadFile.transferTo(file);
                // S3 파일 업로드
                uploadOnS3(saveFileName, file);
                // 주소 할당
                url = defaultUrl + saveFileName;
                // 파일 삭제
                file.delete();

                //dto정보 입력
                Attach attach = Attach.builder().filePath(url).originFileName(origName).serverFileName(saveFileName).build();
                attachList.add(attach);

            } catch (StringIndexOutOfBoundsException e) {
                url = null;
            }
        return url;
    }
    public List<Attach> uploadReturnAttach(List<MultipartFile> uploadFileList) throws IOException {
        List<Attach> attachList = new ArrayList<>();
        if(uploadFileList.isEmpty()) return null;
        for (MultipartFile uploadFile : uploadFileList) {
            String origName = uploadFile.getOriginalFilename();
            String url;
            try {
                // 확장자를 찾기 위한 코드
                final String ext = origName.substring(origName.lastIndexOf('.'));
                // 파일이름 암호화
                final String saveFileName = getUuid() + ext;
                // 파일 객체 생성
                // System.getProperty => 시스템 환경에 관한 정보를 얻을 수 있다. (user.dir = 현재 작업 디렉토리를 의미함)
                File file = new File(System.getProperty("user.dir") + saveFileName);
                // 파일 변환
                uploadFile.transferTo(file);
                // S3 파일 업로드
                uploadOnS3(saveFileName, file);
                // 주소 할당
                url = defaultUrl + saveFileName;
                // 파일 삭제
                file.delete();

                //dto정보 입력
                Attach attach = Attach.builder().filePath(url).originFileName(origName).serverFileName(saveFileName).build();
                attachList.add(attach);

            } catch (StringIndexOutOfBoundsException e) {
                url = null;
            }

        }
        return attachList;
    }
    private static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    private void uploadOnS3(final String findName, final File file) {
        // AWS S3 전송 객체 생성
        final TransferManager transferManager = new TransferManager(this.s3Client);
        // 요청 객체 생성
        final PutObjectRequest request = new PutObjectRequest(bucket, findName, file);
        // 업로드 시도
        final Upload upload =  transferManager.upload(request);

        try {
            upload.waitForCompletion();
        } catch (AmazonClientException amazonClientException) {
            log.error(amazonClientException.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
    public String urlToKey(String url){
        // 일단 /을 기준으로 자른다
        String[] div  = url.split("/");
        String [] div2 = div[div.length-1].split("\\.");
        return div2[0];
    }
    public void deleteList(List<String> urls){
        for (String url: urls){
            delete(url);
        }
    }
    //key가 머여
    //일단 url을 받은다음에 key로 가공한다음에.. 그걸 지운다..
    public void delete(String url) {
        String key = urlToKey(url);

        try {
            //Delete 객체 생성
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucket, key);
            //Delete
            this.s3Client.deleteObject(deleteObjectRequest);
            System.out.println(String.format("[%s] deletion complete", key));

        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }
}
