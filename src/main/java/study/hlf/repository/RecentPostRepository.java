package study.hlf.repository;

import org.springframework.data.repository.CrudRepository;
import study.hlf.entity.Post;

import java.util.Optional;

public interface RecentPostRepository extends CrudRepository<Post, String> {
    Optional<Post> findByEmail(String email);
}
