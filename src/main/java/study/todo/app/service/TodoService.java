package study.todo.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.todo.app.domain.TodoEntity;
import study.todo.app.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repository;

    public String testService() {
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();

        repository.save(entity);
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        validate(entity);

        repository.save(entity);

        log.info("Entity Id : {} is saved", entity.getId());


        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);

        Optional<TodoEntity> original = repository.findById(entity.getId());
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
        });
        repository.save(entity);

        return retrieve(entity.getUserId());

    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);
        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity", entity.getId(), e);
            throw new RuntimeException("error deleting entity " + entity.getId());
        }
        return retrieve(entity.getUserId());
    }

    private void validate(TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if (entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    public List<TodoEntity> retrieve(String userId) {
        return repository.findByUserId(userId);
    }


}
