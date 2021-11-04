package study.todo.app.repository;

import study.todo.app.domain.TodoEntity;

import java.util.List;

public interface TodoRepositoryCustom {

    List<TodoEntity> findByUserId(String userId);
}
