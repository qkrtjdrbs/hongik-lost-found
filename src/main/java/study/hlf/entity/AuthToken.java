package study.hlf.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36, name = "tokenId")
    private String id;

    private Long userId;

    private boolean expired;

    private LocalDateTime expireDate;

    public AuthToken(Long userId){
        this.userId = userId;
        this.expired = false;
        this.expireDate = LocalDateTime.now().plusMinutes(5L);
    }

    public void useToken(){
        this.expired = true;
    }
}
