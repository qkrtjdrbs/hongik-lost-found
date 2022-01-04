package study.hlf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.dto.CommentDeleteDto;
import study.hlf.dto.CommentFormDto;
import study.hlf.entity.Board;
import study.hlf.entity.Comment;
import study.hlf.entity.User;
import study.hlf.repository.BoardRepository;
import study.hlf.repository.CommentRepository;
import study.hlf.repository.UserRepository;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment writeComment(CommentFormDto form){
        Optional<User> user = userRepository.findById(form.getUser_id());
        Optional<Board> board = boardRepository.findById(form.getBoard_id());
        if(user.isEmpty() || board.isEmpty()){
            return null;
        }
        Comment comment = new Comment(form, user.get(), board.get());
        return commentRepository.save(comment);
    }

    public List<Comment> findBoardComments(Long boardId){
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if(findBoard.isEmpty()){
            return List.of(null);
        }
        return findBoard.get().getComments();
    }

    @Transactional
    public boolean deleteComment(Long id){
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    public boolean editComment(Long id, String content) {
        Optional<Comment> findComment = commentRepository.findById(id);
        if(findComment.isPresent()){
            findComment.get().changeContent(content);
            return true;
        }
        return false;
    }
}
