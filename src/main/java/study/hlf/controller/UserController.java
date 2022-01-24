package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.hlf.dto.HelpIdDto;
import study.hlf.dto.SignUpDto;
import study.hlf.entity.User;
import study.hlf.service.AuthTokenService;
import study.hlf.service.UserService;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static study.hlf.Messages.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthTokenService authTokenService;

    @GetMapping("/new")
    public String signUpForm(Model model){
        model.addAttribute("user", new SignUpDto());
        return "signUp";
    }

    @PostMapping("/new")
    public String signUp(@Valid @ModelAttribute(name = "user") SignUpDto user, BindingResult bindingResult) throws UnsupportedEncodingException {
        if(bindingResult.hasErrors()){
            return "signUp";
        }
        Long saveId = userService.save(user);
        if(saveId == null){
            bindingResult.reject("duplicatedUsername", "이미 가입한 아이디 혹은 이메일입니다.");
            return "signUp";
        }
        authTokenService.sendEmailWithAuthToken(saveId, user.getEmail());
        String message = URLEncoder.encode(AUTH_MAIL_SEND_MESSAGE, "UTF-8");
        return "redirect:/?message="+message;
    }

    @GetMapping("/help/id")
    public String helpIdForm(@ModelAttribute(name = "form") HelpIdDto form){
        return "helpId";
    }

    @PostMapping("/help/id")
    public String helpId(@Valid @ModelAttribute(name = "form") HelpIdDto form,
                         BindingResult bindingResult) throws UnsupportedEncodingException {
        if(bindingResult.hasErrors()){
            return "helpId";
        }
        User findUser = userService.findUserByEmail(form.getEmail());
        if(findUser == null){
            bindingResult.reject("NotExistEmail", "가입되지 않은 이메일입니다.");
            return "helpId";
        }

        userService.sendEmailWithUsername(findUser.getEmail(), findUser.getUsername());

        String message = URLEncoder.encode(MAIL_SEND_MESSAGE, "UTF-8");
        return "redirect:/?message="+message;
    }

}
