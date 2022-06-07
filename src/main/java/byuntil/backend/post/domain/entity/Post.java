package byuntil.backend.post.domain.entity;

import byuntil.backend.post.dto.PostDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @ElementCollection
    @CollectionTable(
            name = "URL",
            joinColumns = @JoinColumn(name = "POST_ID")
    )
    @OrderColumn
    @Column(name = "URL_NAME")
    private List<String> urlList = new ArrayList<>();

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int viewNum;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Attach> attaches = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Builder
    public Post(Long id, String title, String content, int viewNum) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewNum = viewNum;
    }

    public void addAttach(final Attach attach) {
        attaches.add(attach);
    }
    //연관관계 설정
    public void addAttaches(List<Attach> attachList){
        for(Attach attach : attachList){
            attach.addPost(this);
        }
    }
    public void setBoard(Board board){
        this.board = board;
    }

    public void deleteAttaches() {
        attaches.clear();
    }

    public void updatePost(final PostDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.attaches = dto.toEntity().getAttaches();
    }
}
