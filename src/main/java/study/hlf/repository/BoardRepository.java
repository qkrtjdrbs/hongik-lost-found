package study.hlf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.hlf.entity.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    @Override
    @Query("select b from Board b left join fetch b.comments c left join fetch c.user where b.id = :id")
    Optional<Board> findById(@Param("id") Long id);
}
