package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.hlf.Const;
import study.hlf.dto.SubmitDto;
import study.hlf.entity.Board;
import study.hlf.entity.User;
import study.hlf.service.BoardService;

import javax.validation.Valid;
import java.util.List;

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
        Long boardId = boardService.writeBoard(loginUser.getId(), form);
        if(boardId == null){
            bindingResult.reject("submitFail", "서버 오류로 글 저장에 실패했습니다.");
            return "submit";
        }
        return "redirect:/board";
    }

    @GetMapping("/board")
    public String board(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        Model model,
                        @SessionAttribute(required = false, name = Const.LOGIN_USER) User user){
        Page<Board> PagingBoard = boardService.findBoard(pageable);
        List<Board> board = PagingBoard.getContent();
        model.addAttribute("board", board);
        model.addAttribute("pagingList", PagingBoard);
        model.addAttribute("user", user);
        return "board";
    }
}
