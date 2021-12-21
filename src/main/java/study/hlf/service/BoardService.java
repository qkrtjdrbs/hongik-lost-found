package study.hlf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import study.hlf.dto.SubmitDto;
import study.hlf.entity.Board;
import study.hlf.entity.User;
import study.hlf.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Long writeBoard(User user, SubmitDto form){

        Board board = new Board(form);
        board.addUser(user);

        return boardRepository.save(board).getId();
    }

    public Page<Board> findBoard(Pageable pageable){
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort().descending());
        return boardRepository.findAll(pageable);
    }
}
