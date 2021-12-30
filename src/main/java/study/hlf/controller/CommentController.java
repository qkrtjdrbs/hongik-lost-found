package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.hlf.dto.CommentDto;
import study.hlf.dto.CommentFormDto;
import study.hlf.entity.Comment;
import study.hlf.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/write")
    public String save(@RequestBody CommentFormDto form,
                       Model model){
        Comment comment = commentService.writeComment(form);
        if(comment == null){
            throw new RuntimeException();
        }

        model.addAttribute("comment", comment);

        return "fragments/comment";
    }
}
