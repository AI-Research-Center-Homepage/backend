package byuntil.backend.post.service;

import byuntil.backend.common.exception.ApplicationException;
import byuntil.backend.common.exception.BoardNotFoundException;
import byuntil.backend.common.exception.post.PostNotFoundException;
import byuntil.backend.common.exception.s3.UploadFailException;
import byuntil.backend.file.FileStore;
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
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final FileStore fileStore;
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
            List<Attach> attachList = fileStore.storeFiles(fileList);
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


    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public void updatePost(final Long postId, final PostDto postDto, final List<MultipartFile> files) {
        Optional<Post> postOptional = postRepository.findById(postId);
        postOptional.ifPresent(post -> {
            post.setModifiedDate(LocalDateTime.now());
            changeUrlOfAttach(postDto, post, files);
            try {
                changeUrlOfImageList(postDto, post);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            post.updatePost(postDto);
        });
    }


    @Transactional
    public void deletePost(final Long postId) throws UnsupportedEncodingException {
        final Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        for (Attach attach: post.getAttaches()) {
            fileStore.deleteFile(attach.getServerFileName());
        }
        for(String url : post.getImageList()){
            fileStore.deleteFile(url);
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
                        try {
                            fileStore.deleteFile(attach.getServerFileName());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    post.deleteAttaches();
                }
        );
        //2. files를 모두 post에 넣어주기
        Optional.ofNullable(files).ifPresent(
                fileList -> {
                    List<Attach> attachList = null;
                    try {
                        attachList = fileStore.storeFiles(fileList);
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
    public void changeUrlOfImageList(PostDto postDto, Post post) throws UnsupportedEncodingException {
        if(postDto.getImageList()==null){
            Optional.ofNullable(post.getImageList()).ifPresent(
                    list -> {
                        for (String url: list) {
                            try {
                                fileStore.deleteFile(url);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
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
                    fileStore.deleteFile(url);
                }
            }


        }
    }

}
