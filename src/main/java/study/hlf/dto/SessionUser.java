package study.hlf.dto;

import lombok.Getter;
import study.hlf.entity.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private Long id;
    private String username;
    private String email;
    private boolean enabled;

    public SessionUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
    }
}
