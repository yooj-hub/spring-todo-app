package study.todo.app.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.todo.app.domain.TodoEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoServiceTest {

    @Autowired
    TodoService todoService;
    @Test
    void saveTest() {
        //given
        String uuid = UUID.randomUUID().toString();
        TodoEntity entity = TodoEntity.builder().userId(uuid).title("test").build();

        //when
        List<TodoEntity> todoEntityList = todoService.create(entity);
        TodoEntity savedEntity = todoEntityList.get(0);

        //then
        assertThat(todoEntityList.size()).isEqualTo(1);
        assertThat(savedEntity.getTitle()).isEqualTo("test");
        assertThat(savedEntity.getUserId()).isEqualTo(uuid);
    }

}
