package byuntil.backend.post.controller.common;

import byuntil.backend.post.dto.PostDto;
import byuntil.backend.post.dto.response.readAdminAllPostDto;
import byuntil.backend.post.dto.response.readMorePostDto;
import byuntil.backend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class PostAdminController {
    private final PostService postService;


    @GetMapping("/posts/notice")
    public ResponseEntity readNotice(){
        List<readAdminAllPostDto> list = postService.readAllPost("Notice");
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/posts/info")
    public ResponseEntity readNews(){
        List<readAdminAllPostDto> list = postService.readAllPost("Info");
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/posts/source")
    public ResponseEntity readSource(){
        List<readAdminAllPostDto> list = postService.readAllPost("Source");
        return ResponseEntity.ok().body(list);
    }

    //게시글등록
    @PostMapping("/posts")
    public ResponseEntity createPost(@RequestPart PostDto postDto,
                                     @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(postDto, fileList));

    }
    //게시글삭제
    @DeleteMapping("/posts")
    public ResponseEntity deletePost(@RequestParam Long id){
        postService.deletePost(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
    //게시글 조회
    @GetMapping("/posts")
    public ResponseEntity readPost(@RequestParam Long id){
        readMorePostDto dto = postService.findById(id).toReadAdminDto();
        //만약에 해당하는 id로 조회된 post가 없을 경우 예외 터뜨리기 -> service부분에 예외터뜨리는 부분 있음
        return ResponseEntity.ok().body(dto);
    }
    //게시글 수정
    @PutMapping("/posts")
    public ResponseEntity updatePost(@RequestParam Long id, @RequestPart PostDto postDto,
                                        @RequestPart(value = "file", required = false)List<MultipartFile> files){
        postService.updatePost(id, postDto, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}
