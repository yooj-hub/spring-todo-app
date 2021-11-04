package study.todo.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.todo.web.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {


    @GetMapping
    public String testControoler() {
        return "Hello World";
    }

    @GetMapping("/testGetMapping")
    public String testControllerWithPath() {
        return "Hello World testGetMapping";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
        return "Hello World! Id " + id;
    }

    @GetMapping("/testRequestParam")
    public String testControllerRequestParam(@RequestParam(required = false) int id) {
        return "Hello World! Id " + id;
    }

    @GetMapping("/testRequestBody")
    public String testControllerRequestBody(@RequestBody TestRequestBodyDto testRequestBodyDto) {
        return "Hello World! ID " + testRequestBodyDto.getId() + " Message " + testRequestBodyDto.getMessage();
    }

    @GetMapping("/testResponseBody")
    public ResponseDto<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseDto");
        return ResponseDto
                .<String>builder()
                .data(list)
                .build();
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseEntity. And you got 400!");
        ResponseDto<String> response = ResponseDto
                .<String>builder()
                .data(list)
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}
