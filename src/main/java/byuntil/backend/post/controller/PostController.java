package byuntil.backend.post.controller;

import byuntil.backend.post.dto.AttachDto;
import byuntil.backend.post.dto.PostDto;
import byuntil.backend.post.service.AttachService;
import byuntil.backend.post.service.PostService;
import byuntil.backend.response.DefaultRes;
import byuntil.backend.response.ResponseMessage;
import byuntil.backend.response.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final AttachService attachService;
    private final PostService postService;
    public static final DefaultRes FAIL_DEFAULT_RES
            = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveProfile(@RequestPart PostDto postDto, @RequestPart(value = "profile", required = false) final List<MultipartFile> multipartFileList) throws IOException {
        postService.save(postDto, multipartFileList);
        //TODO : 컴파일타임에 잡을 수 있는 예외를 런타임으로 넘겨버릴 수 있기 때문에 사용을 지양해야한다 제네릭 타입으로 return하기
    }
}
