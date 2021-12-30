package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.hlf.Const;
import study.hlf.dto.CommentFormDto;
import study.hlf.entity.Comment;
import study.hlf.entity.User;
import study.hlf.service.CommentService;


@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/write")
    public String save(@RequestBody CommentFormDto form,
                       Model model,
                       @SessionAttribute(name = Const.LOGIN_USER, required = false) User loginUser){
        Comment comment = commentService.writeComment(form);
        if(comment == null){
            throw new RuntimeException();
        }

        model.addAttribute("comment", comment);
        if(loginUser != null){
            model.addAttribute("user", loginUser);
        }

        return "fragments/comment";
    }
}
