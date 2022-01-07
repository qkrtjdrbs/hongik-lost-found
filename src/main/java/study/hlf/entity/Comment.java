package study.hlf.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.hlf.dto.CommentFormDto;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

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

    public void addParent(Comment parent){
        this.parent = parent;
        parent.getChildren().add(this);
    }

    //==비즈니스 메소드==//
    public void changeContent(String content){
        this.content = content;
    }
}
