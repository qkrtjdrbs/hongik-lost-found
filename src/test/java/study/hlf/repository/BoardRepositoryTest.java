package study.hlf.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import study.hlf.dto.SubmitDto;
import study.hlf.entity.Board;
import study.hlf.entity.BoardStatus;
import study.hlf.entity.Role;
import study.hlf.entity.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class BoardRepositoryTest {

    @Autowired BoardRepository boardRepository;
    @Autowired EntityManager em;

    @Test
    void basicCRUDTest(){
        Board post = new Board(new SubmitDto("test", "save"), new User("user", "123", "a@a.com", Role.ROLE_USER));
        Board savedPost = boardRepository.save(post);

        assertThat(post).isEqualTo(savedPost);

        Optional<Board> findPost = boardRepository.findById(savedPost.getId());
        assertThat(findPost.get().getId()).isEqualTo(savedPost.getId());

        Board nothing = boardRepository.findById(987654321L).orElse(null);
        assertThat(nothing).isNull();

        savedPost.changeContent("changed content");
        savedPost.changeTitle("changed title");

        em.flush();
        em.clear();

        Optional<Board> changedPost = boardRepository.findById(savedPost.getId());
        assertThat(changedPost.get().getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(changedPost.get().getContent()).isEqualTo(savedPost.getContent());

        boardRepository.deleteById(changedPost.get().getId());

        em.flush();
        em.clear();

        List<Board> board = boardRepository.findAll();
        assertThat(board.size()).isEqualTo(0);
    }

    @Test
    void dynamicSearchTest(){
        User userA = new User("userA", "123", "a@a.com", Role.ROLE_USER);
        User userB = new User("userB", "123", "a@a.com", Role.ROLE_USER);
        Board post1 = new Board(new SubmitDto("test1", "save1"), userA);
        Board post2 = new Board(new SubmitDto("test2", "save2"), userA);
        Board post3 = new Board(new SubmitDto("test3", "save3"), userB);
        Board post4 = new Board(new SubmitDto("test4", "save4"), userB);
        Board post5 = new Board(new SubmitDto("test5", "save5"), userB);

        boardRepository.save(post1);
        boardRepository.save(post2);
        boardRepository.save(post3);
        boardRepository.save(post4);
        boardRepository.save(post5);

        BoardSearch searchByUsernameAndContent = new BoardSearch("userA", null, "save", BoardStatus.LOST);
        Page<Board> result = boardRepository.findBoardDynamic(searchByUsernameAndContent, PageRequest.of(0, 1));
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.hasNext()).isTrue();
        assertThat(result.getContent().get(0).getUser().getUsername()).isEqualTo("userA");
        assertThat(result.getContent().get(0).getContent()).startsWith("save");

        BoardSearch searchByUsernameAndStatus = new BoardSearch("userB", null, null, BoardStatus.FOUND);
        Page<Board> nothing = boardRepository.findBoardDynamic(searchByUsernameAndStatus, PageRequest.of(0, 3));
        assertThat(nothing.getContent().size()).isEqualTo(0);

        BoardSearch searchByNoCondition = new BoardSearch(null, null, null, null);
        Page<Board> likeFindAll = boardRepository.findBoardDynamic(searchByNoCondition, PageRequest.of(0, 5));
        assertThat(likeFindAll.getContent().size()).isEqualTo(5);
    }
}