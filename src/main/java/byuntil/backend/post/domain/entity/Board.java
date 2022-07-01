package byuntil.backend.post.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Board(final String name, final List<Post> posts) {
        this.name = name;
        this.posts = posts;
    }
}
