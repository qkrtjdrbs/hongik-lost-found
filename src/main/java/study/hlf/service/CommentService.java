package study.hlf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.dto.CommentDeleteDto;
import study.hlf.dto.CommentFormDto;
import study.hlf.dto.NestedCommentFormDto;
import study.hlf.entity.Board;
import study.hlf.entity.Comment;
import study.hlf.entity.User;
import study.hlf.exception.NotAuthorizedException;
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
        board.get().addCommentCount();
        Comment saveComment = commentRepository.save(comment);
        saveComment.setGroupId(saveComment.getId());
        return saveComment;
    }

    @Transactional
    public Comment writeNestedComment(NestedCommentFormDto form){
        Optional<User> user = userRepository.findById(form.getUser_id());
        Optional<Board> board = boardRepository.findById(form.getBoard_id());
        Optional<Comment> parent = commentRepository.findById(form.getParent_id());
        if(user.isEmpty() || board.isEmpty() || parent.isEmpty()){
            return null;
        }
        Comment comment = new Comment(form, user.get(), board.get());
        comment.addParent(parent.get());
        comment.setGroupId(parent.get().getGroupId());

        board.get().addCommentCount();
        return commentRepository.save(comment);
    }

    public Page<Comment> findBoardComments(Long boardId, int page){
        return commentRepository.findComments(boardId, PageRequest.of(page, 10));
    }

    @Transactional
    public boolean deleteComment(Long commentId, Long postId, Long requestUserId){
        try {
            Optional<Comment> findComment = commentRepository.findById(commentId);
            findComment.ifPresentOrElse((comment -> {
                if(!requestUserId.equals(comment.getUser().getId())){
                    throw new NotAuthorizedException();
                }
                if(comment.getChildren().size() != 0){
                    comment.changeStatus();
                    boardRepository.findById(postId).get().subCommentCount();
                } else {
                    Comment parent = comment.getParent();
                    if(parent != null && comment.getParent().isDeleteStatus()
                            && comment.getParent().getChildren().size() == 1){
                        commentRepository.deleteById(commentId);
                        commentRepository.deleteById(parent.getId());
                        boardRepository.findById(postId).get().subCommentCount();
                    } else {
                        commentRepository.deleteById(commentId);
                        boardRepository.findById(postId).get().subCommentCount();
                    }
                }
            }), null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    public boolean editComment(Long id, String content, Long requestUserId) {
        Optional<Comment> findComment = commentRepository.findById(id);
        if(findComment.isPresent()){
            if(!requestUserId.equals(findComment.get().getUser().getId())){
                return false;
            }
            findComment.get().changeContent(content);
            return true;
        }
        return false;
    }
}
