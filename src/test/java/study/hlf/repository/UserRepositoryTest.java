package study.hlf.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.entity.Role;
import study.hlf.entity.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveTest(){
        User user = new User("user", "123", "qwe", Role.USER);
        User savedUser = userRepository.save(user);

        assertThat(user).isEqualTo(savedUser);
    }

    @Test
    void findByUsernameTest(){
        User user1 = new User("user1", "123", "qwe", Role.USER);
        User user2 = new User("user2", "123", "qwe", Role.USER);
        userRepository.save(user1);
        userRepository.save(user2);

        Optional<User> findUser1 = userRepository.findByUsername("user1");
        Optional<User> findUser3 = userRepository.findByUsername("user3");

        assertThat(findUser1.get().getUsername()).isEqualTo("user1");
        assertThat(findUser3).isEmpty();
    }

}