package study.todo.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.todo.app.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);

    Boolean existsByEmail(String email);

    UserEntity findByEmailAndPassword(String email, String password);
}
