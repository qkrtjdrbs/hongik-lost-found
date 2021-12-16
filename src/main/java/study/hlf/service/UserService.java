package study.hlf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.hlf.dto.SignUpDto;
import study.hlf.entity.User;
import study.hlf.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long save(SignUpDto user) {
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());
        if (findUser.isPresent()) {
            return null;
        }
        user.encodePassword();
        return userRepository.save(new User(user)).getId();
    }
}
