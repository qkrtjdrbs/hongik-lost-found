package study.hlf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.dto.SubmitDto;
import study.hlf.entity.Board;
import study.hlf.repository.BoardRepository;
import study.hlf.repository.BoardSearch;
import study.hlf.repository.UserRepository;

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

    @Transactional
    public void editPost(Long id, SubmitDto form){
        try {
            Board post = this.findOneById(id);
            post.changeTitle(form.getTitle());
            post.changeContent(form.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Transactional
    public Board findOneById(Long id){
        Optional<Board> findPost = boardRepository.findById(id);
        findPost.ifPresent(Board::addHit);
        return findPost.orElse(null);
    }

    @Transactional
    public void deletePost(Long postId){
        try{
            boardRepository.deleteById(postId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public Page<Board> findBoard(Pageable pageable){
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort().descending());
        return boardRepository.findAll(pageable);
    }*/

    public Page<Board> searchBoardDynamic(BoardSearch condition, Pageable pageable){
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort().descending());
        return boardRepository.findBoardDynamic(condition, pageable);
    }
}
