package study.hlf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.hlf.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
