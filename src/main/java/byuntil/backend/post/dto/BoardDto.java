package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.Board;
import byuntil.backend.post.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
    private String name;
    private ArrayList<PostDto> postDtoList;

    @Builder
    public BoardDto(String name){
        this.name = name;
    }

    public void addPostDtoList(ArrayList<PostDto> list){
        this.postDtoList = list;
    }

    public Board toEntity(){
        return Board.builder().name(name).build();
    }
}
