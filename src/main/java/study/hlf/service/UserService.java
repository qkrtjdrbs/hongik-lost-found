package study.hlf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.dto.LoginDto;
import study.hlf.dto.SignUpDto;
import study.hlf.entity.User;
import study.hlf.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long save(SignUpDto user) {
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());
        if (findUser.isPresent()) {
            return null;
        }
        user.setEncoder(encoder);
        user.encodePassword();
        return userRepository.save(new User(user)).getId();
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User login(LoginDto user){
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());
        return findUser
                .filter(u -> encoder.matches(user.getPassword(), u.getPassword()))
                .orElse(null);
    }
}
