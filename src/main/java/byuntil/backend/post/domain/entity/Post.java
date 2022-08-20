package byuntil.backend.post.domain.entity;

import byuntil.backend.post.dto.PostDto;
import byuntil.backend.post.dto.response.readMorePostDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private String author;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "URL",
            joinColumns = @JoinColumn(name = "POST_ID")
    )
    @OrderColumn
    @Column(name = "URL_NAME")
    private List<String> imageList = new ArrayList<>();

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int viewNum;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attach> attaches = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Builder
    public Post(final Long id, final String title, final List<String> images,
                final String content, final int viewNum, final String author, final LocalDateTime createdDate, final LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.imageList = images;
        this.content = content;
        this.viewNum = viewNum;
        this.author = author;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
    }

    public void addAttach(final Attach attach) {
        attaches.add(attach);
    }

    public void setImageList(List<String> imageList){
        this.imageList = imageList;
    }
    //연관관계 설정
    public void addAttaches(final List<Attach> attachList) {
        for (Attach attach : attachList) {
            attach.addPost(this);
        }
    }
    public void setCreatedDate(final LocalDateTime createdDate){
        this.createdDate = createdDate;
    }
    public void setModifiedDate(final LocalDateTime modifiedDate){
        this.modifiedDate = modifiedDate;
    }

    public void setBoard(final Board board) {
        this.board = board;
        //이거 해주어야하지않나? -> cascade 설정되어있어서 괜찮음
        //board.getPosts().add(this);
    }

    public void deleteAttaches() {
        attaches.clear();
    }

    public void updatePost(final PostDto dto) {
        this.title = dto.getTitle();
        this.imageList = dto.getImageList();
        this.content = dto.getContent();
        this.imageList = dto.getImageList();
        //attach는 따로 update
        this.author = dto.getAuthor();
    }
    public PostDto toDto(){
        return PostDto.builder().boardName(board.getName()).content(content).title(title).author(author)
                .id(id).images(imageList).build();
    }
    public readMorePostDto toReadAdminDto(){
        return new readMorePostDto(this);
    }
}
