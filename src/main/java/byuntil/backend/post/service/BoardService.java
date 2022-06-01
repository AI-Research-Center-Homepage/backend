package byuntil.backend.post.service;

import byuntil.backend.post.domain.entity.Board;
import byuntil.backend.post.domain.repository.BoardRepository;
import byuntil.backend.post.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    @Transactional
    public Long save(BoardDto boardDto){
        return boardRepository.save(boardDto.toEntity()).getId();
    }
    public Optional<Board> findById(Long id){
        return boardRepository.findById(id);
    }
    @Transactional
    public void delete(Board board){
        boardRepository.delete(board);
    }
}