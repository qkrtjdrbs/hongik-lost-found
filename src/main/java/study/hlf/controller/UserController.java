package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import study.hlf.dto.SignUpDto;
import study.hlf.service.AuthTokenService;
import study.hlf.service.UserService;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@Slf4j
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
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.info("bindingResult = {}", fieldError.getCode());
            }
            return "signUp";
        }
        Long saveId = userService.save(user);
        if(saveId == null){
            bindingResult.reject("duplicatedUsername", "이미 존재하는 아이디 입니다.");
            return "signUp";
        }
        authTokenService.sendEmailWithAuthToken(saveId, user.getEmail());
        String message = "인증 메일이 발송되었습니다.";
        message = URLEncoder.encode(message, "UTF-8");
        return "redirect:/?message="+message;
    }
}
