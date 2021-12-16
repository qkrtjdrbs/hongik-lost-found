package study.hlf.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.hlf.entity.User;

import static study.hlf.Const.LOGIN_USER;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String login(Model model){
        log.info("login controller");
        return "login";
    }
}
