package study.hlf.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.hlf.dto.SubmitDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    private int commentCount;
    private int hits;

    @ManyToOne(fetch = FetchType.LAZY, cascade = PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = ALL)
    private List<Comment> comments = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private BoardStatus status;

    public Board(SubmitDto form, User user){
        this.title = form.getTitle();
        this.content = form.getContent();
        this.commentCount = 0;
        this.hits = 0;
        this.status = BoardStatus.LOST;
        this.addUser(user);
    }

    //==연관관계 설정 메소드==//
    public void addUser(User user){
        this.user = user;
        user.getBoards().add(this);
    }

    //==비즈니스 메소드==//
    public void addHit(){
        this.hits++;
    }

    public void addCommentCount(){
        this.commentCount++;
    }

    public void subCommentCount(){
        this.commentCount--;
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
