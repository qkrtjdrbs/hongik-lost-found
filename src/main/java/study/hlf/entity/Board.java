package study.hlf.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.hlf.dto.SubmitDto;

import javax.persistence.*;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    private int hits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Board(SubmitDto form){
        this.title = form.getTitle();
        this.content = form.getContent();
        this.hits = 0;
    }

    //==연관관계 설정 메소드==//
    public void addUser(User user){
        this.user = user;
    }

    //==비즈니스 메소드==//
    public void addHit(){
        this.hits++;
    }
}
