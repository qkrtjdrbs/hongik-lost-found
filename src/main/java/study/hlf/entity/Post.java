package study.hlf.entity;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 보관 기간은 1초, 보관 되어 있다는 건 1초 이내에 해당 이메일 계정이 글을 작성했다는 것
 * @Indexed : id가 아닌 특정 필드로 데이터 검색하려고 할 때, 모든 데이터를 순회하려면 오버 헤드가 매우 클 것이므로,
 * 별도의 index(secondary index)를 설정하여 id 값을 저장, 필드 값으로 데이터 조회시 index에서 id 목록을 반환 받아 실제 데이터 조회 가능 
 */

@Getter
@RedisHash(value = "post", timeToLive = 1)
public class Post {
    @Id
    private String id;
    @Indexed
    private String email;

    public Post(String email){
        this.email = email;
    }
}
