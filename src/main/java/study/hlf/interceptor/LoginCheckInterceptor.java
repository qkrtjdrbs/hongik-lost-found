package study.hlf.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import study.hlf.Const;
import study.hlf.dto.SessionUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("{} : 로그인 체크 인터셉터 실행", requestURI);

        HttpSession session = request.getSession();
        SessionUser login = (SessionUser) session.getAttribute(Const.LOGIN_USER);

        if(login == null){
            response.sendRedirect("/user/login?redirectURL=" + requestURI);
            return false;
        }
        if(!requestURI.equals("/auth/email/resend") && !login.isEnabled()){
            log.info("이메일 미인증 사용자 : 게시판으로 redirect");
            response.sendRedirect("/board");
            return false;
        }
        log.info("로그인 유저 : {}", login.getUsername());

        return true;
    }
}
