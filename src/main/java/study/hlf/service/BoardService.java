package study.hlf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.dto.SubmitDto;
import study.hlf.entity.Board;
import study.hlf.entity.User;
import study.hlf.repository.BoardRepository;
import study.hlf.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long writeBoard(Long userId, SubmitDto form){
        Board board = new Board(form, userRepository.findById(userId).get());
        return boardRepository.save(board).getId();
    }

    public Page<Board> findBoard(Pageable pageable){
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort().descending());
        return boardRepository.findAll(pageable);
    }
}
