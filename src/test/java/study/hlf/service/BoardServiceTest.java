package study.hlf.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import study.hlf.dto.SubmitDto;
import study.hlf.entity.Board;
import study.hlf.entity.BoardStatus;
import study.hlf.entity.Role;
import study.hlf.entity.User;
import study.hlf.repository.BoardRepository;
import study.hlf.repository.BoardSearch;
import study.hlf.repository.UserRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Autowired
    private EntityManager em;

    @Test
    void writeBoardTest(){
        User user = new User("test", "123", "a", Role.USER);
        SubmitDto dto = new SubmitDto("t", "c");
        Board board = new Board(dto, user);
        ReflectionTestUtils.setField(board, "id", 1L);
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(boardRepository.save(any())).willReturn(board);

        Long boardId = boardService.writeBoard(2L, dto, null, null);
        assertThat(boardId).isEqualTo(1L);
    }

    @Test
    void findOneByIdTest(){
        User user = new User("test", "123", "a", Role.USER);
        SubmitDto dto = new SubmitDto("t", "c");
        Board board = new Board(dto, user);
        ReflectionTestUtils.setField(board, "id", 1L);
        given(boardRepository.findById(1L)).willReturn(Optional.of(board));

        Board findPost = boardService.findPostById(1L);

        assertThat(findPost.getTitle()).isEqualTo("t");
        assertThat(findPost.getContent()).isEqualTo("c");
        assertThat(findPost.getHits()).isEqualTo(1);
    }

    @Test
    void editPostTest(){
        User user = new User("test", "123", "a", Role.USER);
        SubmitDto dto = new SubmitDto("t", "c");
        Board board = new Board(dto, user);
        ReflectionTestUtils.setField(board, "id", 1L);
        given(boardRepository.findById(1L)).willReturn(Optional.of(board));

        boardService.editPost(1L, new SubmitDto("c_t", "c_c"), user.getId());
        assertThat(board.getTitle()).isEqualTo("c_t");
        assertThat(board.getContent()).isEqualTo("c_c");
    }

    @Test
    void searchBoardDynamicTest(){
        User userA = new User("testA", "123", "a", Role.USER);
        User userB = new User("testB", "123", "a", Role.USER);
        SubmitDto dtoA = new SubmitDto("t1", "c1");
        SubmitDto dtoB = new SubmitDto("t2", "c2");
        SubmitDto dtoC = new SubmitDto("t3", "c3");
        Board boardA = new Board(dtoA, userA);
        Board boardB = new Board(dtoA, userB);
        Board boardC = new Board(dtoB, userA);
        Board boardD = new Board(dtoC, userB);
        List<Board> boards = new ArrayList<>();
        boards.add(boardA);
        boards.add(boardB);
        boards.add(boardC);
        boards.add(boardD);
        BoardSearch findAllLost = new BoardSearch(null, null, null, BoardStatus.LOST);
        PageRequest zeroToTwo = PageRequest.of(1, 2);
        given(boardRepository.findBoardDynamic(
                findAllLost,
                PageRequest.of(0, 2)))
                .willReturn(new PageImpl<>(boards.subList(0, 2), PageRequest.of(0, 2), boards.size()));

        Page<Board> findBoard = boardService.searchBoardDynamic(findAllLost, zeroToTwo);
        assertThat(findBoard.getTotalPages()).isEqualTo(2);
        assertThat(findBoard.getNumber()).isEqualTo(0);
    }
}