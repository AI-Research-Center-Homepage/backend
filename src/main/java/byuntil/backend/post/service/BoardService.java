package byuntil.backend.post.service;

import byuntil.backend.post.domain.entity.Board;
import byuntil.backend.post.domain.repository.BoardRepository;
import byuntil.backend.post.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(final BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    public Optional<Board> findById(final Long id) {
        return boardRepository.findById(id);
    }


    public Board findByName(final String name) {
        return boardRepository.findByName(name).get();
    }

    @Transactional
    public void delete(final Board board) {
        boardRepository.delete(board);
    }
}
