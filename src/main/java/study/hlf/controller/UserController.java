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
import study.hlf.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/new")
    public String signUpForm(Model model){
        model.addAttribute("user", new SignUpDto());
        return "signUp";
    }

    @PostMapping("/new")
    public String signUp(@Valid @ModelAttribute(name = "user") SignUpDto user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("DTO = {}", user);
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
        return "redirect:/login";
    }
}
