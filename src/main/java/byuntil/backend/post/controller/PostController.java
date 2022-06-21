package byuntil.backend.post.controller;

import byuntil.backend.common.exception.s3.BadRequestException;
import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.dto.AttachDto;
import byuntil.backend.post.dto.DownloadAttachDto;
import byuntil.backend.post.dto.PostDto;
import byuntil.backend.post.service.AttachService;
import byuntil.backend.post.service.PostService;
import byuntil.backend.response.DefaultRes;
import byuntil.backend.response.ResponseMessage;
import byuntil.backend.response.StatusCode;
import byuntil.backend.s3.service.S3ServiceImpl;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String region;

    @PostMapping(path = "/save", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void saveProfile(@RequestPart PostDto postDto,
                            @RequestPart(value = "attachedFile", required = false)
                            final List<MultipartFile> multipartFileList) throws IOException {
        System.out.println("===========================ddd===");
        postService.save(postDto, multipartFileList);
        //TODO : 컴파일타임에 잡을 수 있는 예외를 런타임으로 넘겨버릴 수 있기 때문에 사용을 지양해야한다 제네릭 타입으로 return하기
    }
    @PostMapping(path = "/attach/submit")
    public void saveAttach(@RequestPart(value="file1") MultipartFile file, PostDto postDto) throws IOException {
        System.out.println(file.getOriginalFilename());
        Attach attach =s3Service.uploadReturnAttach(file).get();
        //TODO: postDto랑 잘 엮어서 attach저장하는 로직
    }

    @GetMapping("/download")
    public boolean download(DownloadAttachDto attachDto, HttpServletRequest request, HttpServletResponse response) {
        String fileKey = attachDto.getFileKey();
        String downloadFileName = attachDto.getFileName();

        if (fileKey == null) {
            return false;
        }
        S3Object fullObject = null;
        try {
            fullObject = s3Service.getObject(bucketName, fileKey);
            if (fullObject == null) {
                return false;
            }
        } catch (AmazonS3Exception e) {
            throw new BadRequestException("다운로드 파일이 존재하지 않습니다.");
        }

        OutputStream os = null;
        FileInputStream fis = null;
        boolean success = false;
        try {
            S3ObjectInputStream objectInputStream = fullObject.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(objectInputStream);

            String fileName = null;
            if(downloadFileName != null) {
                //fileName= URLEncoder.encode(downloadFileName, "UTF-8").replaceAll("\\+", "%20");
                fileName=  getEncodedFilename(request, downloadFileName);
            } else {
                fileName=  getEncodedFilename(request, fileKey); // URLEncoder.encode(fileKey, "UTF-8").replaceAll("\\+", "%20");
            }

            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader( "Content-Disposition", "attachment; filename=\"" + fileName + "\";" );
            response.setHeader("Content-Length", String.valueOf(fullObject.getObjectMetadata().getContentLength()));
            response.setHeader("Set-Cookie", "fileDownload=true; path=/");
            FileCopyUtils.copy(bytes, response.getOutputStream());
            success = true;
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                log.debug(e.getMessage(), e);
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                log.debug(e.getMessage(), e);
            }
        }
        return success;
    }

    /**
     * 파일명이 한글인 경우 URL encode이 필요함.
     * @param request
     * @param displayFileName
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getEncodedFilename(HttpServletRequest request, String displayFileName) throws UnsupportedEncodingException {
        String header = request.getHeader("User-Agent");

        String encodedFilename = null;
        if (header.indexOf("MSIE") > -1) {
            encodedFilename = URLEncoder.encode(displayFileName, "UTF-8").replaceAll("\\+", "%20");
        } else if (header.indexOf("Trident") > -1) {
            encodedFilename = URLEncoder.encode(displayFileName, "UTF-8").replaceAll("\\+", "%20");
        } else if (header.indexOf("Chrome") > -1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < displayFileName.length(); i++) {
                char c = displayFileName.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            encodedFilename = sb.toString();
        } else if (header.indexOf("Opera") > -1) {
            encodedFilename = "\"" + new String(displayFileName.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (header.indexOf("Safari") > -1) {
            encodedFilename = URLDecoder.decode("\"" + new String(displayFileName.getBytes("UTF-8"), "8859_1") + "\"", "UTF-8");
        } else {
            encodedFilename = URLDecoder.decode("\"" + new String(displayFileName.getBytes("UTF-8"), "8859_1") + "\"", "UTF-8");
        }
        return encodedFilename;

    }
    @PostMapping("/submit")
    public String submit(@ModelAttribute PostDto postDto) throws IOException {
        System.out.println("==============================");
        postDto.setTitle("임시제목1");
        postDto.setBoardName("게시판1");
        System.out.println(postDto.getContent());
        System.out.println("==============================");
        //출력해보기
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
