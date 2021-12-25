package study.hlf.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.hlf.dto.CommentFormDto;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Lob
    private String content;

    @Enumerated(value = EnumType.STRING)
    private CommentStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Comment(CommentFormDto form, User user, Board board){
        this.content = form.getContent();
        this.status = CommentStatus.NO_CHILD;
        addUser(user);
        addBoard(board);
    }

    //==연관관계 설정 메소드==//
    public void addUser(User user){
        this.user = user;
        user.getComments().add(this);
    }

    public void addBoard(Board board){
        this.board = board;
        board.getComments().add(this);
    }
}
