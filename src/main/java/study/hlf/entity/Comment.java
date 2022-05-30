package study.hlf.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.hlf.dto.CommentFormDto;
import study.hlf.dto.NestedCommentFormDto;

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

    private Long groupId;

    @Lob
    private String content;

    private boolean deleteStatus = false;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    // 자식 입장에서 자식(Many) to 부모(One)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    // 부모 입장에서 부모(One) to 자식(Many)
    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    public Comment(CommentFormDto form, User user, Board board){
        this.content = form.getContent();
        addUser(user);
        addBoard(board);
    }

    public Comment(NestedCommentFormDto form, User user, Board board){
        this.content = form.getContent();
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

    public void setGroupId(Long id){
        this.groupId = id;
    }

    public void changeStatus(){
        this.deleteStatus = true;
    }
}
