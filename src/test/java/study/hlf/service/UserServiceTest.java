package study.hlf.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.dto.LoginDto;
import study.hlf.dto.SignUpDto;
import study.hlf.entity.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired BCryptPasswordEncoder encoder;

    @BeforeEach
    void addUser(){
        SignUpDto user = new SignUpDto("user", "123", "test");
        userService.save(user);
    }

    @Test
    void 회원가입(){
        Optional<User> user = userService.findByUsername("user");
        assertThat(user).isNotNull();
        assertThat(user.get().getId()).isEqualTo(1L);

        SignUpDto user2 = new SignUpDto("user", "123e", "testc");
        Long id = userService.save(user2);
        assertThat(id).isNull();
    }

    @Test
    void 로그인(){
        User loginUser = userService.login(new LoginDto("user", "123"));
        assertThat(loginUser.getUsername()).isEqualTo("user");

        User wrongPassword = userService.login(new LoginDto("user", "1232"));
        assertThat(wrongPassword).isNull();

        User notExistUsername = userService.login(new LoginDto("user1", "123"));
        assertThat(notExistUsername).isNull();
    }
}