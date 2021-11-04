package study.todo.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import study.todo.app.domain.TodoEntity;


import java.util.List;

import static study.todo.app.domain.QTodoEntity.todoEntity;

public class TodoRepositoryImpl implements TodoRepositoryCustom {
    @Autowired
    JPAQueryFactory queryFactory;

    @Override
    public List<TodoEntity> findByUserId(String userId) {
        return queryFactory
                .selectFrom(todoEntity)
                .where(todoEntity.userId.eq(userId))
                .fetch();
    }
}
