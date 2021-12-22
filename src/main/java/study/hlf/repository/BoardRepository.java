package study.hlf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.hlf.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
}
