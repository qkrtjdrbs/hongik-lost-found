package study.hlf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.dto.CommentFormDto;
import study.hlf.entity.Board;
import study.hlf.entity.Comment;
import study.hlf.entity.User;
import study.hlf.repository.BoardRepository;
import study.hlf.repository.CommentRepository;
import study.hlf.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long writeComment(CommentFormDto form){
        Optional<User> user = userRepository.findById(form.getUser_id());
        Optional<Board> board = boardRepository.findById(form.getBoard_id());
        if(user.isEmpty() || board.isEmpty()){
            return null;
        }
        Comment comment = new Comment(form, user.get(), board.get());
        return commentRepository.save(comment).getId();
    }
}
