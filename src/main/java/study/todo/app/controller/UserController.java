package study.todo.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.todo.app.domain.UserEntity;
import study.todo.app.security.TokenProvider;
import study.todo.app.service.UserService;
import study.todo.web.dto.ResponseDto;
import study.todo.web.dto.UserDto;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            UserEntity user = UserEntity.builder()
                    .email(userDto.getEmail())
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .build();
            UserEntity registeredUser = userService.create(user);

            UserDto responseUserDto = UserDto.builder()
                    .email(registeredUser.getEmail())
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();
            return ResponseEntity.ok().body(responseUserDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .error(e.getMessage())
                    .build());
        }

    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
        UserEntity user = userService.getByCredentials(
                userDto.getEmail(),
                userDto.getPassword());

        if (user != null) {
            final String token = tokenProvider.create(user);
            final UserDto responseUserDto = UserDto.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .id(user.getId())
                    .token(token)
                    .username(user.getUsername()).build();
            return ResponseEntity.ok().body(responseUserDto);
        } else {
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .error("Login failed")
                            .build());
        }

    }


}
