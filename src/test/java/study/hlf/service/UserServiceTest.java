/*
package study.hlf.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import study.hlf.dto.LoginDto;
import study.hlf.dto.SignUpDto;
import study.hlf.entity.User;
import study.hlf.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    void saveUserTest_success(){
        SignUpDto dto = new SignUpDto("test1", "123", "a@a.com");
        User user = new User(dto, true);
        ReflectionTestUtils.setField(user, "id", 1L);

        given(userRepository.findByUsername(dto.getUsername())).willReturn(Optional.empty());
        given(userRepository.save(any())).willReturn(user);
        given(encoder.encode(dto.getPassword())).willReturn("abc");

        Long saveId = userService.save(dto);

        assertThat(saveId).isEqualTo(1L);
    }

    @Test
    void saveUserTest_fail(){
        SignUpDto dto = new SignUpDto("test1", "123", "a@a.com");
        User user = new User(dto, true);
        ReflectionTestUtils.setField(user, "id", 1L);

        given(userRepository.findByUsername(dto.getUsername())).willReturn(Optional.of(user));

        Long saveId = userService.save(dto);

        assertThat(saveId).isNull();
    }

    @Test
    void loginTest_success(){
        SignUpDto dto = new SignUpDto("test1", "123", "a@a.com");
        User user = new User(dto, true);
        ReflectionTestUtils.setField(user, "id", 1L);
        LoginDto loginDto = new LoginDto("test1", "123");

        given(userRepository.findByUsername(loginDto.getUsername())).willReturn(Optional.of(user));
        given(encoder.matches(loginDto.getPassword(), user.getPassword())).willReturn(true);

        User loginUser = userService.login(loginDto);

        assertThat(loginUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void loginTest_wrongPassword(){
        SignUpDto dto = new SignUpDto("test1", "123", "a@a.com");
        User user = new User(dto, true);
        ReflectionTestUtils.setField(user, "id", 1L);
        LoginDto loginDto = new LoginDto("test1", "1234");

        given(userRepository.findByUsername(loginDto.getUsername())).willReturn(Optional.of(user));
        given(encoder.matches(loginDto.getPassword(), user.getPassword())).willReturn(false);

        User loginUser = userService.login(loginDto);

        assertThat(loginUser).isNull();
    }

    @Test
    void loginTest_notExistingUser(){
        LoginDto loginDto = new LoginDto("test1", "1234");

        given(userRepository.findByUsername(loginDto.getUsername())).willReturn(Optional.ofNullable(null));

        User loginUser = userService.login(loginDto);

        assertThat(loginUser).isNull();
    }
}
*/
