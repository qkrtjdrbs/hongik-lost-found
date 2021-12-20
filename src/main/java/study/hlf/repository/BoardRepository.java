package study.hlf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.hlf.entity.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByTitleLike(String username);
}
