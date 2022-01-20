package study.hlf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.hlf.entity.AuthToken;

public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {
}
