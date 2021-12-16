package study.hlf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.hlf.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
