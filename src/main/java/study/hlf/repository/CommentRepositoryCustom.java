package study.hlf.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.hlf.entity.Comment;

public interface CommentRepositoryCustom {
    Page<Comment> findComments(Long postId, Pageable pageable);
}
