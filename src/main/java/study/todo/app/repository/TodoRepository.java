package study.todo.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.todo.app.domain.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, String>, TodoRepositoryCustom {
}
