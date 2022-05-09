package byuntil.backend.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@PropertySource("classpath:application-aws.properties")
@ActiveProfiles("test")
@SpringBootTest
@Import(S3MockConfig.class)
public class MockTest {
    //@Value("${cloud.aws.s3.bucket}")
    static String bucket = "aihomepage";
    //mock 객체는 가짜 객체이며 그 안에 메소드 호출해서 사용하려면 반드시 스터빙(stubbing)을 해야합니다.

    @Autowired
    static private AmazonS3 amazonS3;

    //BeforeAll에서 S3Mock서버를 실행시켜야하고 test에서 사용할 Bucket을 미리 생성해둔다
    @BeforeAll
    static void setUp(@Autowired S3Mock s3Mock, @Autowired AmazonS3 amazonS3) {
        s3Mock.start();
        amazonS3.createBucket(bucket);
    }
    @AfterAll
    static void tearDown(@Autowired S3Mock s3Mock, @Autowired AmazonS3 amazonS3) {
        amazonS3.shutdown();
        s3Mock.stop();
    }


    //error
    @DisplayName("s3 import 테스트")
    @Test
    //s3에 bucket, path, content를 집어넣고 다시 꺼냈을 때 정상적으로 기록되었는지 확인
    void S3Import() throws IOException {
        //given
        String path = "test/02.txt";
        String contentType = "text/plain";
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path, new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8)), objectMetadata);
        amazonS3.putObject(putObjectRequest);
        // when
        S3Object s3Object = amazonS3.getObject(bucket, path);
        // then
        org.assertj.core.api.Assertions.assertThat(s3Object.getObjectMetadata().getContentType()).isEqualTo(contentType);
        org.assertj.core.api.Assertions.assertThat(new String(FileCopyUtils.copyToByteArray(s3Object.getObjectContent()))).isEqualTo("");
    }


}
