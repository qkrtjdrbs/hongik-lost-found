package study.hlf.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import study.hlf.Const;
import study.hlf.dto.SessionUser;
import study.hlf.entity.Board;
import study.hlf.entity.User;
import study.hlf.service.BoardService;
import study.hlf.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class CommentAlarmHandler extends TextWebSocketHandler {

    public static final int MAX_COMMENT_LENGTH = 20;
    private final UserService userService;
    private final BoardService boardService;

    // <email, session> map
    Map<String, WebSocketSession> userSessionsMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("afterConnectionEstablished 실행");
        Map<String, Object> httpSession = session.getAttributes();
        SessionUser sessionUser = (SessionUser) httpSession.get(Const.LOGIN_USER);
        if(sessionUser != null){
            log.info("로그인 유저 이메일 : {}", sessionUser.getEmail());
            userSessionsMap.put(sessionUser.getEmail(), session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("메시지 payload : {}", payload);
        if(!payload.isEmpty()){
            String str[] = payload.split(",");

            if(str.length == 4){
                Long receiverId = Long.parseLong(str[0]);
                Long boardId = Long.parseLong(str[1]);
                Long commentId = Long.parseLong(str[2]);
                String content = str[3];

                User receiver = userService.findUserById(receiverId);
                WebSocketSession receiverSession = userSessionsMap.get(receiver.getEmail());
                log.info("리시버 세션 : {}", receiverSession);

                // 현재 글 작성자가 접속중!
                if(receiverSession != null){
                    log.info("리시버가 접속 중이므로 알람을 전송합니다.");
                    Board post = boardService.justFindPostById(boardId);
                    String substring = content.substring(0, Math.min(content.length(), MAX_COMMENT_LENGTH));
                    if(substring.length() == MAX_COMMENT_LENGTH){
                        substring += "...";
                    }
                    TextMessage textMessage = new TextMessage("<a href='/board/" + boardId +
                            "#comment" + commentId + "'>" +
                            post.getTitle() + " 게시물에 " +
                            "새 댓글이 달렸습니다 : " + substring + "</a>");
                    receiverSession.sendMessage(textMessage);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Map<String, Object> httpSession = session.getAttributes();
        SessionUser sessionUser = (SessionUser) httpSession.get(Const.LOGIN_USER);
        log.info("로그인 유저 : {}", sessionUser);
        if(sessionUser != null){
            log.info("closed 세션 email : {}", sessionUser.getEmail());
            userSessionsMap.remove(sessionUser.getEmail());
        }
    }
}
