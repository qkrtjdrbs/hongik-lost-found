package study.hlf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.hlf.dto.SubmitDto;
import study.hlf.entity.Board;
import study.hlf.entity.User;
import study.hlf.repository.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Long writeBoard(User user, SubmitDto form){

        Board board = new Board(form);
        board.addUser(user);

        return boardRepository.save(board).getId();
    }
}
