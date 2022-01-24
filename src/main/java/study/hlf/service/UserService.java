package study.hlf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
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
    private final EmailService emailService;

    @Transactional
    public Long save(SignUpDto user) {
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());
        Optional<User> findUser2 = userRepository.findByEmail(user.getEmail());
        if (findUser.isPresent() || findUser2.isPresent()) {
            return null;
        }
        user.setEncoder(encoder);
        user.encodePassword();
        return userRepository.save(new User(user, false)).getId();
    }

    public void sendEmailWithUsername(String email, String username){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("홍익 분실물 센터 아이디 찾기");
        mailMessage.setText("아이디는 " + username + " 입니다.");
        emailService.sendEmail(mailMessage);
    }

    public User findUserByEmail(String email){
        Optional<User> findUser = userRepository.findByEmail(email);
        return findUser.orElse(null);
    }

    public User findUserById(Long id){
        Optional<User> findUser = userRepository.findById(id);
        return findUser.orElse(null);
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
