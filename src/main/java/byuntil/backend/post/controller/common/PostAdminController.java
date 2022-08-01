package byuntil.backend.post.controller.common;

import byuntil.backend.post.dto.PostDto;
import byuntil.backend.post.dto.response.readAllPostDto;
import byuntil.backend.post.dto.response.readPostDto;
import byuntil.backend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class PostAdminController {
    private final PostService postService;


    @GetMapping("/posts/notice")
    public ResponseEntity readNotice(){
        return ResponseEntity.ok().body(readAllPostDto.builder().list(postService.readAllPost("Notice")).build());
    }
    @GetMapping("/posts/news")
    public ResponseEntity readNews(){
        return ResponseEntity.ok().body(readAllPostDto.builder().list(postService.readAllPost("News")).build());
    }
    @GetMapping("/posts/source")
    public ResponseEntity readSource(){
        return ResponseEntity.ok().body(readAllPostDto.builder().list(postService.readAllPost("Source")).build());
    }

    //게시글등록
    @PostMapping("/posts")
    public ResponseEntity createPost(@RequestPart PostDto postDto, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws IOException {

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
        PostDto dto = postService.findById(id).toDto();
        //만약에 해당하는 id로 조회된 post가 없을 경우 예외 터뜨리기 -> service부분에 예외터뜨리는 부분 있음
        return ResponseEntity.ok().body(dto);
    }
    //게시글 수정
    @PutMapping("/posts")
    public ResponseEntity updatePost(@RequestParam Long id, @RequestPart PostDto postDto,
                                        @RequestPart(value = "file", required = false)MultipartFile file){
        postService.updatePost(id, postDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}