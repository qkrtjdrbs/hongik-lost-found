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
import java.util.Random;

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

    @Transactional
    public void sendEmailWithTempPassword(User user){
        String tempPassword = makeTempPassword();
        user.changePassword(encoder.encode(tempPassword));

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("홍익 분실물 센터 임시 비밀번호");
        mailMessage.setText("임시 비밀번호는 " + tempPassword + " 입니다. " +
                "로그인 후 본인의 비밀번호로 변경해주세요.");
        emailService.sendEmail(mailMessage);
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

    private String makeTempPassword() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int maxSize = 10;

        Random random = new Random();

        return random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(maxSize)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
