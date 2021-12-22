package study.hlf.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.hlf.entity.Board;

public interface BoardRepositoryCustom {
    Page<Board> findBoardDynamic(BoardSearch condition, Pageable pageable);
}
