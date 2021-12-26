package study.hlf;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.dto.SignUpDto;
import study.hlf.dto.SubmitDto;
import study.hlf.entity.Board;
import study.hlf.entity.User;
import study.hlf.service.BoardService;
import study.hlf.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final UserService userService;
        private final BoardService boardService;

        public void dbInit(){
            for(int i=1;i<10;i++){
                userService.save(new SignUpDto("data"+i, "1234", "a@a.com"));
            }
            Optional<User> user1 = userService.findByUsername("data1");
            Optional<User> user2 = userService.findByUsername("data2");
            for(int i=1;i<200;i++){
                SubmitDto form = new SubmitDto("제목" + i, "테스트");
                if(i % 2 == 0) boardService.writeBoard(user1.get().getId(), form);
                else boardService.writeBoard(user2.get().getId(), form);
            }
        }
    }
}
