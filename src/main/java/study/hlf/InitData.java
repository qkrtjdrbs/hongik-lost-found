package study.hlf;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import study.hlf.dto.SignUpDto;
import study.hlf.service.UserService;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Slf4j
@Component
public class InitData {

    private final UserService userService;

    @PostConstruct
    public void init(){
        for(int i=1;i<3;i++){
            userService.save(new SignUpDto("data"+i, "1234", "a@a.com"));
        }
    }
}
