package study.hlf.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class SignUpDto {

    private BCryptPasswordEncoder encoder;

    @NotBlank
    @Size(min = 4, max = 8)
    private String username;

    @Size(min = 4, max = 8)
    private String password;

    @Email
    @NotNull
    private String email;

    public SignUpDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setEncoder(BCryptPasswordEncoder encoder){
        this.encoder = encoder;
    }

    public void encodePassword(){
        this.password = encoder.encode(password);
    }
}
