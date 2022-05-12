package byuntil.backend.post.controller;

import byuntil.backend.post.dto.AttachDto;
import byuntil.backend.post.service.AttachService;
import byuntil.backend.response.DefaultRes;
import byuntil.backend.response.ResponseMessage;
import byuntil.backend.response.StatusCode;
import byuntil.backend.s3.service.S3ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("/attach")
public class AttachController {
    private final S3ServiceImpl s3Service;
    private final AttachService attachService;
    public static final DefaultRes FAIL_DEFAULT_RES
            = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    //생성자주입 -> 변수를 final로 만들 수 있음
    public AttachController(S3ServiceImpl s3Service, AttachService attachService) {
        this.s3Service = s3Service;
        this.attachService = attachService;
    }
    @GetMapping("/test")
    public String test(){
        return "ok";
    }


    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity saveProfile(@RequestPart AttachDto dto, @RequestPart(value = "profile", required = false) final MultipartFile multipartFile) {
        try {
            return new ResponseEntity(attachService.save(dto, multipartFile), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //TODO : 컴파일타임에 잡을 수 있는 예외를 런타임으로 넘겨버릴 수 있기 때문에 사용을 지양해야한다 제네릭 타입으로 return하기
    }
}
