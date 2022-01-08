package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.hlf.Const;
import study.hlf.dto.CommentDeleteDto;
import study.hlf.dto.CommentEditDto;
import study.hlf.dto.CommentFormDto;
import study.hlf.dto.NestedCommentFormDto;
import study.hlf.entity.Comment;
import study.hlf.entity.User;
import study.hlf.exception.NotAuthorizedException;
import study.hlf.service.CommentService;


@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/write")
    public String save(@RequestBody CommentFormDto form,
                       Model model,
                       @SessionAttribute(name = Const.LOGIN_USER, required = false) User loginUser){
        if(loginUser == null || loginUser.getId() != form.getUser_id()){
            log.info("권한 없음");
            throw new NotAuthorizedException();
        }

        Comment comment = commentService.writeComment(form);
        if(comment == null){
            throw new RuntimeException();
        }

        model.addAttribute("comment", comment);
        model.addAttribute("postId", form.getBoard_id());
        if(loginUser != null){
            model.addAttribute("user", loginUser);
        }

        return "fragments/comment";
    }

    @PostMapping("/comment/nested/write")
    public String nestedSave(@RequestBody NestedCommentFormDto form,
                             Model model,
                             @SessionAttribute(name = Const.LOGIN_USER, required = false) User loginUser){
        if(loginUser == null || loginUser.getId() != form.getUser_id()){
            log.info("권한 없음");
            throw new NotAuthorizedException();
        }

        Comment comment = commentService.writeNestedComment(form);
        if(comment == null){
            throw new RuntimeException();
        }

        model.addAttribute("comment", comment);
        model.addAttribute("postId", form.getBoard_id());
        if(loginUser != null){
            model.addAttribute("user", loginUser);
        }

        return "fragments/comment";
    }

    @PostMapping("/comment/{id}/delete")
    public ResponseEntity delete(@RequestBody CommentDeleteDto dto,
                                 @SessionAttribute(name = Const.LOGIN_USER, required = false) User loginUser){
        if(loginUser == null || loginUser.getId() != dto.getUserId()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean deleted = commentService.deleteComment(dto.getCommentId(), dto.getPostId());
        return (deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/comment/{id}/edit")
    public ResponseEntity edit(@RequestBody CommentEditDto dto,
                                 @SessionAttribute(name = Const.LOGIN_USER, required = false) User loginUser){
        if(loginUser == null || loginUser.getId() != dto.getUser_id()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean edited = commentService.editComment(dto.getComment_id(), dto.getContent());
        return (edited ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
