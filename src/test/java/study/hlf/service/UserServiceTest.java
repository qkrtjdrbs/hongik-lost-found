package study.hlf.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import study.hlf.dto.SignUpDto;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired BCryptPasswordEncoder encoder;

    @Test
    void 회원가입(){
        SignUpDto user = new SignUpDto("user", "123", "test");
        user.setEncoder(encoder);
        Long saveId = userService.save(user);
        assertThat(saveId).isEqualTo(1);

        SignUpDto user2 = new SignUpDto("user", "123e", "testc");
        user2.setEncoder(encoder);
        Long id = userService.save(user2);
        assertThat(id).isNull();
    }
}