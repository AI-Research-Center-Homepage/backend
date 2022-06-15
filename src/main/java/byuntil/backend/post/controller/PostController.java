package byuntil.backend.post.controller;

import byuntil.backend.post.dto.AttachDto;
import byuntil.backend.post.dto.BoardDto;
import byuntil.backend.post.dto.PostDto;
import byuntil.backend.post.service.AttachService;
import byuntil.backend.post.service.PostService;
import byuntil.backend.response.DefaultRes;
import byuntil.backend.response.ResponseMessage;
import byuntil.backend.response.StatusCode;
import byuntil.backend.s3.service.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final AttachService attachService;
    private final S3ServiceImpl s3Service;
    private final PostService postService;
    public static final DefaultRes FAIL_DEFAULT_RES
            = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    @PostMapping(path = "/save", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void saveProfile(@RequestPart PostDto postDto,
                            @RequestPart(value = "attachedFile", required = false)
                            final List<MultipartFile> multipartFileList) throws IOException {
        System.out.println("===========================ddd===");
        postService.save(postDto, multipartFileList);
        //TODO : 컴파일타임에 잡을 수 있는 예외를 런타임으로 넘겨버릴 수 있기 때문에 사용을 지양해야한다 제네릭 타입으로 return하기
    }
    @PostMapping("/submit")
    public String submit(@ModelAttribute PostDto postDto) throws IOException {
        System.out.println("==============================");
        postDto.setTitle("임시제목1");
        postDto.setBoardDto(new BoardDto("게시판1"));
        System.out.println(postDto.getContent());
        System.out.println("==============================");
        //출력해보기
        for (String url : postDto.getUrlList()) {
            System.out.println(url);
        }
        postService.save(postDto, null);
        return "redirect:/";
    }
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        //근데 이거 무조건 하나씩들어오던데
        System.out.println("==============================");
        System.out.println("==============================");
        String urlList = s3Service.upload(multipartFile);

        return urlList;
    }

}
