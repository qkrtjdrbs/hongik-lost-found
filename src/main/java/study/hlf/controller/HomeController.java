package study.hlf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.hlf.Const;
import study.hlf.dto.SessionUser;
import study.hlf.entity.User;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(
            @SessionAttribute(required = false, name = Const.LOGIN_USER) SessionUser loginUser,
            Model model
    ){
        model.addAttribute("user", loginUser);
        return "home";
    }
}
