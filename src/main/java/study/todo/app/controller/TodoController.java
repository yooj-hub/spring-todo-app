package study.todo.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.todo.app.domain.TodoEntity;
import study.todo.app.service.TodoService;
import study.todo.web.dto.ResponseDto;
import study.todo.web.dto.TodoDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        List<String> list = new ArrayList<>();
        list.add(service.testService());
        return ResponseEntity.ok().body((ResponseDto) ResponseDto.<String>builder()
                .data(list).build());
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDto dto) {
        try {
            String temporaryUserId = "temporary-user";

            TodoEntity entity = TodoDto.toEntity(dto);

            entity.setId(null);

            entity.setUserId(temporaryUserId);

            List<TodoEntity> entities = service.create(entity);

            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(toList());

            return ResponseEntity.ok().body(ResponseDto.<TodoDto>builder().data(dtos).build());

        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(ResponseDto.<TodoDto>builder().error(error));
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDto dto) {
        String temporaryUserId = "temporary-user";

        TodoEntity entity = TodoDto.toEntity(dto);
        entity.setUserId(temporaryUserId);

        List<TodoEntity> entities = service.update(entity);

        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(toList());

        return ResponseEntity.ok().body(ResponseDto.<TodoDto>builder().data(dtos).build());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDto dto) {
        try {
            String temporaryUserId = "temporary-user";
            TodoEntity entity = TodoDto.toEntity(dto);
            entity.setUserId(temporaryUserId);
            List<TodoEntity> entities = service.delete(entity);
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(toList());
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(ResponseDto.<TodoDto>builder().error(error));
        }
    }


}
