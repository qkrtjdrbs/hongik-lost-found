package study.hlf.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.hlf.Const;
import study.hlf.Messages;
import study.hlf.dto.SessionUser;
import study.hlf.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static study.hlf.Messages.*;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(
            @SessionAttribute(required = false, name = Const.LOGIN_USER) SessionUser loginUser,
            Model model,
            @RequestParam(defaultValue = "") String message,
            HttpServletRequest request
    ){
        log.info("home message : {}", message);
        model.addAttribute("user", loginUser);
        if(message.equals(SUCCESSFUL_AUTHENTICATED_MESSAGE)){
            HttpSession session = request.getSession(false);
            if(session != null){
                session.invalidate();
            }
            message = message + " " + RELOGIN_MESSAGE;
        }
        model.addAttribute("message", message);
        return "home";
    }
}
