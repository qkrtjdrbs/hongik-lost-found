package study.hlf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.hlf.entity.AuthToken;
import study.hlf.entity.User;
import study.hlf.service.AuthTokenService;
import study.hlf.service.UserService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

import static study.hlf.Messages.*;

/**
 * 토큰 검증, 재전송
 */

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final AuthTokenService authTokenService;
    private final UserService userService;

    @GetMapping("/auth/email")
    public String handleAuthToken(@RequestParam String token) throws UnsupportedEncodingException {

        String message;

        AuthToken findToken = authTokenService.findAuthTokenById(token);
        if(findToken == null || findToken.isExpired() || findToken.getExpireDate().isBefore(LocalDateTime.now())){
            message = EXPIRED_TOKEN_MESSAGE;
            message = URLEncoder.encode(message, "UTF-8");
            return "redirect:/?message="+message;
        }

        User findUser = userService.findUserById(findToken.getUserId());
        if(findUser == null){
            message = NO_USER_MESSAGE;
            message = URLEncoder.encode(message, "UTF-8");
            return "redirect:/?message="+message;
        }
        if(findUser.isEnabled()){
            message = ALREADY_AUTHENTICATED_USER_MESSAGE;
            message = URLEncoder.encode(message, "UTF-8");
            return "redirect:/?message="+message;
        }

        authTokenService.authenticateUser(findUser, findToken);

        message = SUCCESSFUL_AUTHENTICATED_MESSAGE;
        message = URLEncoder.encode(message, "UTF-8");
        return "redirect:/?message="+message;
    }

    @GetMapping("/auth/email/resend")
    public String resendAuthToken(@RequestParam Long userId) throws UnsupportedEncodingException {
        User findUser = userService.findUserById(userId);
        authTokenService.sendEmailWithAuthToken(userId, findUser.getEmail());
        String message = MAIL_SEND_MESSAGE;
        message = URLEncoder.encode(message, "UTF-8");
        return "redirect:/?message="+message;
    }
}
