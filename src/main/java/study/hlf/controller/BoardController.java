package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.hlf.Const;
import study.hlf.dto.PostDeleteDto;
import study.hlf.dto.SessionUser;
import study.hlf.dto.SubmitDto;
import study.hlf.entity.Board;
import study.hlf.entity.Comment;
import study.hlf.entity.Post;
import study.hlf.entity.User;
import study.hlf.exception.NotAuthorizedException;
import study.hlf.repository.BoardSearch;
import study.hlf.repository.RecentPostRepository;
import study.hlf.service.BoardService;
import study.hlf.service.CommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final RecentPostRepository recentPostRepository;

    @GetMapping("/submit")
    public String boardSubmitForm(@ModelAttribute(name = "form") SubmitDto form){
        return "submit";
    }

    @PostMapping("/submit")
    public String boardSubmit(
            @Valid @ModelAttribute(name = "form") SubmitDto form,
            BindingResult bindingResult,
            @RequestParam Double longitude,
            @RequestParam Double latitude,
            @SessionAttribute(required = false, name = Const.LOGIN_USER) SessionUser loginUser
    ){
        if(bindingResult.hasErrors()){
            log.info("에러 : {}", bindingResult.getFieldError());
            return "submit";
        }

        // 로그인 유저가 최근 글 작성한 적 있는지 확인
        Optional<Post> findUserPost = recentPostRepository.findByEmail(loginUser.getEmail());
        if(findUserPost.isPresent()){
            bindingResult.reject("shortTermFail");
            return "submit";
        }

        Long boardId = boardService.writeBoard(loginUser.getId(), form, longitude, latitude);
        if(boardId == null){
            bindingResult.reject("submitPostFail");
            return "submit";
        }
        
        // 최근 글 작성 정보 redis 저장
        Post post = new Post(loginUser.getEmail());
        recentPostRepository.save(post);

        return "redirect:/board/" + boardId;
    }

    @GetMapping("/board")
    public String board(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        @ModelAttribute BoardSearch boardSearch,
                        Model model,
                        @SessionAttribute(required = false, name = Const.LOGIN_USER) SessionUser user){
        Page<Board> pagingBoard = boardService.searchBoardDynamic(boardSearch, pageable);
        List<Board> board = pagingBoard.getContent();
        String url = requestURL(boardSearch);

        log.info("요청 url = {}", url);

        model.addAttribute("url", url);
        model.addAttribute("board", board);
        model.addAttribute("pagingList", pagingBoard);
        model.addAttribute("user", user);

        return "board";
    }

    @GetMapping("/board/{id}")
    public String post(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        @ModelAttribute BoardSearch boardSearch,
                        @PathVariable Long id,
                        Model model,
                        @SessionAttribute(required = false, name = Const.LOGIN_USER) SessionUser user){
        Page<Board> pagingBoard = boardService.searchBoardDynamic(boardSearch, pageable);
        List<Board> board = pagingBoard.getContent();
        Board post = boardService.findPostById(id);
        Page<Comment> comments = commentService.findBoardComments(id, 0);
        String url = requestURL(boardSearch);

        log.info("요청 url = {}", url);

        model.addAttribute("id", id);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("url", url);
        model.addAttribute("board", board);
        model.addAttribute("pagingList", pagingBoard);
        model.addAttribute("user", user);

        return "post";
    }

    @PostMapping("/board/{id}/delete")
    public ResponseEntity delete(@RequestBody PostDeleteDto dto,
                                 @SessionAttribute(required = false, name = Const.LOGIN_USER) SessionUser loginUser){
        if(!loginUser.getId().equals(dto.getUserId())){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        boardService.deletePost(dto.getPostId(), dto.getUserId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/board/{id}/edit")
    public String edit(@PathVariable Long id,
                       Model model,
                       @SessionAttribute(required = false, name = Const.LOGIN_USER) SessionUser loginUser){
        Board post = boardService.justFindPostById(id);
        if(!post.getUser().getId().equals(loginUser.getId())){
            throw new NotAuthorizedException();
        }
        model.addAttribute("form",
                new SubmitDto(post.getTitle(),
                        post.getContent(),
                        post.getStatus(),
                        post.getCoord().getLongitude(),
                        post.getCoord().getLatitude()));

        return "submit";
    }

    @PostMapping("/board/{id}/edit")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute(name = "form") SubmitDto form,
                       BindingResult bindingResult,
                       @SessionAttribute(required = false, name = Const.LOGIN_USER) SessionUser loginUser){
        if(bindingResult.hasErrors()){
            return "submit";
        }

        boardService.editPost(id, form, loginUser.getId());

        return "redirect:/board/" + id;
    }

    private String requestURL(BoardSearch boardSearch) {
        String url = "/board";
        String parameters = "";
        if(boardSearch.getUsername() != null){
            if(hasLength(parameters)){
                parameters += "&";
            }
            parameters = parameters.concat("username="+ boardSearch.getUsername());
        }
        if(boardSearch.getTitle() != null){
            if(hasLength(parameters)){
                parameters += "&";
            }
            parameters = parameters.concat("title="+ boardSearch.getTitle());
        }
        if(boardSearch.getContent() != null){
            if(hasLength(parameters)){
                parameters += "&";
            }
            parameters = parameters.concat("content="+ boardSearch.getContent());
        }
        if(boardSearch.getStatus() != null){
            if(hasLength(parameters)){
                parameters += "&";
            }
            parameters = parameters.concat("status="+ boardSearch.getStatus().name());
        }
        if(hasLength(parameters)){
            return url + "?" + parameters;
        } else {
            return url;
        }
    }
}
