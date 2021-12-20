package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.hlf.Const;
import study.hlf.dto.SubmitDto;
import study.hlf.entity.User;
import study.hlf.service.BoardService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/submit")
    public String boardSubmitForm(@ModelAttribute(name = "form") SubmitDto form){
        return "submit";
    }

    @PostMapping("/submit")
    public String boardSubmit(
            @Valid @ModelAttribute(name = "form") SubmitDto form,
            BindingResult bindingResult,
            @SessionAttribute(required = false, name = Const.LOGIN_USER) User loginUser
    ){
        if(bindingResult.hasErrors()){
            return "submit";
        }
        Long boardId = boardService.writeBoard(loginUser, form);
        if(boardId == null){
            bindingResult.reject("submitFail", "서버 오류로 글 저장에 실패했습니다.");
            return "submit";
        }
        return "redirect:/board";
    }

    @GetMapping("/board")
    public String board(){
        return "board";
    }
}
