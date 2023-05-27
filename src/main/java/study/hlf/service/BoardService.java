package study.hlf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.dto.SubmitDto;
import study.hlf.entity.Board;
import study.hlf.entity.Coord;
import study.hlf.exception.NotAuthorizedException;
import study.hlf.repository.BoardRepository;
import study.hlf.repository.BoardSearch;
import study.hlf.repository.RecentPostRepository;
import study.hlf.repository.UserRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long writeBoard(Long userId, SubmitDto form, Double longitude, Double latitude){
        Board board = new Board(form, userRepository.findById(userId).get());
        board.changeCoord(new Coord(longitude, latitude));
        return boardRepository.save(board).getId();
    }

    @Transactional
    public void editPost(Long id, SubmitDto form, Long requestUserId){
        try {
            Board post = this.justFindPostById(id);
            if(!requestUserId.equals(post.getUser().getId())){
                throw new NotAuthorizedException();
            }
            post.changeTitle(form.getTitle());
            post.changeContent(form.getContent());
            post.changeCoord(new Coord(form.getLongitude(), form.getLatitude()));
            post.changeStatus(form.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Transactional
    public Board findPostById(Long id){
        Optional<Board> findPost = boardRepository.findById(id);
        findPost.ifPresent(Board::addHit);
        return findPost.orElse(null);
    }

    public Board justFindPostById(Long id){
        Optional<Board> findPost = boardRepository.findById(id);
        return findPost.orElse(null);
    }

    @Transactional
    public void deletePost(Long postId, Long requestUserId){
        try{
            Optional<Board> findPost = boardRepository.findById(postId);
            findPost.ifPresent((post) -> {
                if(!requestUserId.equals(post.getUser().getId())){
                    throw new NotAuthorizedException();
                }
            });
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
