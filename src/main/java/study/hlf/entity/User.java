package study.hlf.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String username;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

}
