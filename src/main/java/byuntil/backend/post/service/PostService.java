package byuntil.backend.post.service;

import byuntil.backend.common.exception.ApplicationException;
import byuntil.backend.common.exception.BoardNotFoundException;
import byuntil.backend.common.exception.post.PostNotFoundException;
import byuntil.backend.common.exception.s3.UploadFailException;
import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.Board;
import byuntil.backend.post.domain.entity.Post;
import byuntil.backend.post.domain.repository.BoardRepository;
import byuntil.backend.post.domain.repository.PostRepository;
import byuntil.backend.post.dto.PostDto;
import byuntil.backend.s3.domain.FileStatus;
import byuntil.backend.s3.service.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final AttachService attachService;
    private final S3ServiceImpl s3Service;
    private final BoardRepository boardRepository;


    //minji
    @Transactional
    public Long save(PostDto postDto, List<MultipartFile> fileList) throws IOException {
        //
        Board board = Board.builder().name("게시판1").build();
        boardRepository.save(board);

        Post post = postDto.toEntity();
        List<Attach> attachList =s3Service.upload(fileList);
        post.addAttaches(attachList);
        //보드 이름으로 보드 찾아오는 명령어 수행해야함 없으면 예외터뜨리기
        Optional<Board> result=boardRepository.findByName(postDto.getBoardDto().getName());
        if(!result.isPresent()){
            throw new BoardNotFoundException("게시판 이름이 존재하지 않습니다.");
        }
        //찾아온 board로
        post.setBoard(result.get());
        //그럼 cascade설정으로 attach도 같이 저장이 될 것이다
        postRepository.save(post);

        return post.getId();

    }

    /*
    성재
    문제 1. file 하나가 아니라 여러개가 들어가야한다는점
    2. dto안에 entity가 들어가면 안됨
    3. board도 처리해야함
    @Transactional
    public Long save(PostDto postDto, MultipartFile file) {
        Post post = postDto.toEntity();
        Optional<FileStatus> fileStatusOptional = fileUpload(file);
        fileStatusOptional.ifPresent(fileStatus -> {
            String url = fileStatus.fileUrl();
            Attach attach = Attach.builder()
                    .filePath(url)
                    .originFileName(file.getName())
                    .serverFileName(createStoreFilename(file.getName()))
                    .build();
            attach.addPost(post);
        });
        return postRepository.save(post).getId();
    }*/

    private Optional<FileStatus> fileUpload(MultipartFile file) {
        if (Objects.isNull(file)) {
            return Optional.empty();
        } else if (file.isEmpty()) {
            return Optional.empty();
        }

        try {
            return s3Service.uploadPostFile(file);
        } catch (ApplicationException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UploadFailException();
        }
    }

    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        /*String ext = extractExt(originalFilename);
        String storeFilename = uuid + ext;*/
        String storeFilename = uuid + originalFilename;

        return storeFilename;
    }

    private String extractExt(String originalFilename) {
        int idx = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(idx);
        return ext;
    }

    /*public List<NewsAndNoticePreviewMapping> findAllNewsPost() {
        return newsPostRepository.findAllNews();
    }*/

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public void updatePost(final Long postId, final PostDto request, final MultipartFile file) {
        Optional<Post> postOptional = postRepository.findById(postId);
        postOptional.ifPresent(post -> {
            post.updatePost(request);
            fileUpload(file).ifPresent(fileStatus -> {
                //todo: 이게 표면적으로 지워지긴 하지만 s3에는 지워지지 않음 나중에 추가
                post.deleteAttaches();
                String url = fileStatus.fileUrl();
                Attach build = Attach.builder()
                        .filePath(url)
                        .originFileName(file.getName())
                        .serverFileName(createStoreFilename(file.getName()))
                        .build();
                build.addPost(post);
                post.addAttach(build);
            });
        });
    }

    @Transactional
    public void deletePost(final Long postId) {
        final Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
    }

    @Transactional
    public int updateView(Long id) {
        return postRepository.updateView(id);
    }

}
