package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.hlf.dto.LoginDto;
import study.hlf.entity.User;
import study.hlf.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static study.hlf.Const.LOGIN_USER;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(@ModelAttribute("user") LoginDto user,
                        @SessionAttribute(name = LOGIN_USER, required = false) User loginUser
    ){
        log.info("login controller");
        if(loginUser == null){
            log.info("not login");
            return "login";
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("user") LoginDto user,
            BindingResult bindingResult,
            HttpServletRequest request,
            @RequestParam(defaultValue = "/") String redirectURL
    ){
        if(bindingResult.hasErrors()){
            return "login";
        }

        User loginUser = userService.login(user);
        if(loginUser == null){
            bindingResult.reject("loginFail", "아이디가 없거나 잘못된 비밀번호입니다.");
            return "login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, loginUser);

        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}
