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
import byuntil.backend.post.dto.response.*;
import byuntil.backend.s3.domain.FileStatus;
import byuntil.backend.s3.service.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final AttachService attachService;
    private final S3ServiceImpl s3Service;
    private final BoardRepository boardRepository;


    //file이 들어오는 경우
    @Transactional
    public Long save(final PostDto postDto, final List<MultipartFile> fileList) throws IOException {

        //지워야하는코드
        Board board1 = Board.builder().name("Notice").build();
        if(!boardRepository.findByName("Notice").isPresent()) boardRepository.save(board1);

        Board board2 = Board.builder().name("Info").build();
        if(!boardRepository.findByName("Info").isPresent()) boardRepository.save(board2);

        Board board3 = Board.builder().name("Article").build();
        if(!boardRepository.findByName("Article").isPresent()) boardRepository.save(board3);


        Post post = postDto.toEntity();
        //보드 이름으로 보드 찾아오는 명령어 수행해야함 없으면 예외터뜨리기
        Board board = boardRepository.findByName(postDto.getBoardName()).orElseThrow(BoardNotFoundException::new);
        //찾아온 board로
        post.setBoard(board);
        post.setCreatedDate(LocalDateTime.now());
        //그럼 cascade설정으로 attach도 같이 저장이 될 것이다
        postRepository.save(post);


        if(fileList!=null){
            List<Attach> attachList = s3Service.uploadReturnAttachList(fileList);
            List<FileResponseDto> fileDtoList = new ArrayList<>();

            post.addAttaches(attachList);

            //저장한 뒤여야 attach도 id값이 생긴다
            for (Attach attach: attachList) {
                fileDtoList.add(FileResponseDto.builder().fileName(attach.getOriginFileName()).id(attach.getId())
                        .filePath(attach.getFileUrl()).build());
            }
        }

        return post.getId();
    }

    /*
<<<<<<< HEAD
    성재
    1. file 하나가 아니라 여러개가 들어가야해
    2. dto안에 entity가 들어가면 안될것같아
    3. board도 처리해야해!
=======
    문제 1. file 하나가 아니라 여러개가 들어가야한다는점
    2. dto안에 entity가 들어가면 안됨
    3. board도 처리해야함
>>>>>>> 6f63700fdae2fa7eaba629c57d15c19aa6791d56
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

    private Optional<FileStatus> fileUpload(final MultipartFile file) {
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

    private String createStoreFilename(final String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        /*String ext = extractExt(originalFilename);
        String storeFilename = uuid + ext;*/
        String storeFilename = uuid + originalFilename;

        return storeFilename;
    }

    private String extractExt(final String originalFilename) {
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
    public void updatePost(final Long postId, final PostDto postDto, final List<MultipartFile> files) {
        Optional<Post> postOptional = postRepository.findById(postId);
        postOptional.ifPresent(post -> {
            post.updatePost(postDto);
            post.setModifiedDate(LocalDateTime.now());
            changeUrlOfAttach(postDto, post, files);
            changeUrlOfImageList(postDto, post);
        });
    }


    @Transactional
    public void deletePost(final Long postId) {
        final Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        for (Attach attach: post.getAttaches()) {
            s3Service.delete(attach.getFileUrl());
        }
        for(String url : post.getImageList()){
            s3Service.delete(url);
        }
        postRepository.delete(post);
    }

    @Transactional
    public int updateView(Long id) {
        return postRepository.updateView(id);
    }

    public readPostDto readNotice(Long postId){
        Post post = postRepository.findByBoardNameAndId( "Notice",postId);
        List<Attach> attaches = post.getAttaches();
        List<AttachResponseDto> attachResponseDtos = attaches.stream().map(AttachResponseDto::new).toList();
        readPostDto response = readPostDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .viewNum(post.getViewNum())
                .author(post.getAuthor())
                .attaches(attachResponseDtos)
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
        updateView(postId);
        return response;
    }

    public Optional<PostDto> findById(Long id, String boardName){
        Post post =postRepository.findByBoardNameAndId(boardName, id);
        PostDto postDto = null;
        if(Optional.ofNullable(post).isPresent()){
            postDto = PostDto.builder().title(post.getTitle()).author(post.getAuthor()).content(post.getContent())
                    .boardName(boardName).images(post.getImageList()).build();
        }
        return Optional.ofNullable(postDto);
    }
    public Post findById(final Long postId) {

        return  postRepository.findById(postId).
                orElseThrow(NoSuchElementException::new);
    }


    public List<readAdminAllPostDto> readAllPost(String boardName){
        List<Post> posts = postRepository.findByBoardName(boardName);

        List<readAdminAllPostDto> noticeResponseDtoList = new ArrayList<>();
        for (Post post: posts) {
            noticeResponseDtoList.add(readAdminAllPostDto.builder().title(post.getTitle())
                    .author(post.getAuthor()).createdDate(post.getCreatedDate()).id(post.getId())
                    .modifiedDate(post.getModifiedDate()).viewNum(post.getViewNum()).build());
        }
        return noticeResponseDtoList;

    }
    public void changeUrlOfAttach(PostDto postDto, Post post, List<MultipartFile> files){
        //1. post의 attach모두 제거
        Optional.ofNullable(post.getAttaches()).ifPresent(
                list -> {
                    for (Attach attach: list) {
                        s3Service.delete(attach.getFileUrl());
                    }
                    post.deleteAttaches();
                }
        );
        //2. files를 모두 post에 넣어주기
        Optional.ofNullable(files).ifPresent(
                fileList -> {
                    List<Attach> attachList = null;
                    try {
                        attachList = s3Service.uploadReturnAttachList(fileList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    post.addAttaches(attachList);

                    Board board = boardRepository.findByName(postDto.getBoardName()).orElseThrow(BoardNotFoundException::new);

                    post.setBoard(board);
                    postRepository.save(post);
                }
        );




    }
    public void changeUrlOfImageList(PostDto postDto, Post post){
        if(postDto.getImageList()==null){
            Optional.ofNullable(post.getImageList()).ifPresent(
                    list -> {
                        for (String url: list) {
                            s3Service.delete(url);
                        }
                    }
            );
        }
        else if(post.getImageList() == null){
            //postDto에 있는 imageList를 추가한다
            post.setImageList(postDto.getImageList());
        }
        else{
            //비교해봐서 post에 있던 사진이 postDto에 없다면 삭제해주어야함
            //postDto에 있는 사진들은 이미 추가됐을것이기 때문에 이에 대한 처리는 필요 없음
            for (String url : postDto.getImageList()) {
                if(!post.getImageList().contains(url)){
                    s3Service.delete(url);
                }
            }


        }
    }

}
