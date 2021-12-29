package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.hlf.dto.CommentDto;
import study.hlf.dto.CommentFormDto;
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
        Long id = commentService.writeComment(form);
        if(id == null){
            throw new RuntimeException();
        }

        List<CommentDto> result = commentService.findBoardComments(form.getBoard_id())
                .stream().map((comment) -> new CommentDto(comment.getUser().getUsername(),
                        comment.getContent(), comment.getCreatedDate()))
                .collect(Collectors.toList());

        model.addAttribute("comments", result);

        return "fragments/comments";
    }
}
