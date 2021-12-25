package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import study.hlf.dto.CommentFormDto;
import study.hlf.service.CommentService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/write")
    public String save(@RequestBody CommentFormDto form,
                       HttpServletRequest request){
        Long id = commentService.writeComment(form);
        if(id == null){
            throw new RuntimeException();
        }
        String referer = request.getHeader("referer");
        return "redirect:" + referer;
    }
}
