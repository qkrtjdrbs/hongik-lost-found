package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.hlf.dto.CommentFormDto;
import study.hlf.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/write")
    public String save(@Valid @ModelAttribute("comment") CommentFormDto form,
                       BindingResult bindingResult,
                       HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "post";
        }
        Long id = commentService.writeComment(form);
        if(id == null){
            bindingResult.reject("commentWriteFail");
        }
        String referer = request.getHeader("referer");
        return "redirect:" + referer;
    }
}
