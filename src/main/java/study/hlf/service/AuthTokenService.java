package study.hlf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.entity.AuthToken;
import study.hlf.entity.User;
import study.hlf.repository.AuthTokenRepository;

/**
 * 5분짜리 토큰 저장 후 링크의 쿼리 파라미터에 담아서 이메일 전송
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;
    private final EmailService emailService;

    @Transactional
    public String sendEmailWithAuthToken(Long userId, String email){

        AuthToken authToken = new AuthToken(userId);
        AuthToken saveToken = authTokenRepository.save(authToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("제한 시간은 5분입니다." +
                " http://localhost:8080/auth/email?token="+saveToken.getId());
        emailService.sendEmail(mailMessage);

        return saveToken.getId();
    }

    public AuthToken findAuthTokenById(String id){
        return authTokenRepository.findById(id).orElse(null);
    }

    @Transactional
    public void authenticateUser(User user, AuthToken token){
        user.enabledByToken();
        token.useToken();
    }
}
