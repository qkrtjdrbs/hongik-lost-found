package study.hlf.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.entity.Role;
import study.hlf.entity.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void saveUsers(){
        User user1 = new User("user1", "123", "qwe", Role.ROLE_USER);
        User user2 = new User("user2", "123", "qwe", Role.ROLE_USER);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void findByUsername(){

        Optional<User> findUser1 = userRepository.findByUsername("user1");
        Optional<User> findUser3 = userRepository.findByUsername("user3");

        assertThat(findUser1.get().getUsername()).isEqualTo("user1");
        assertThat(findUser3).isEmpty();
    }

}