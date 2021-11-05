package study.todo.app.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.todo.app.domain.UserEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    static String test_username = "test username";
    static String test_email = "test email";
    static String password = "test!";

    @Test
    void 저장() throws Exception {
        //given

        UserEntity user = UserEntity
                .builder()
                .username(test_username)
                .email(test_email)
                .password(password)
                .build();

        //when
        UserEntity savedUser = userRepository.save(user);

        //then
        assertThat(savedUser.getUsername()).isEqualTo(test_username);
        assertThat(savedUser.getEmail()).isEqualTo(test_email);
        assertThat(savedUser.getPassword()).isEqualTo(password);

        userRepository.delete(savedUser);
    }

    @Test
    void 조회() throws Exception {
        //given
        UserEntity user = UserEntity
                .builder()
                .username(test_username)
                .email(test_email)
                .password(password)
                .build();

        //when
        assertThat(userRepository.existsByEmail(test_email)).isFalse();

        UserEntity savedUser = userRepository.save(user);
        UserEntity findByEmailUser = userRepository.findByEmail(test_email);
        Boolean byEmail = userRepository.existsByEmail(test_email);
        UserEntity byEmailAndPassword = userRepository.findByEmailAndPassword(test_email, password);

        //then
        assertThat(savedUser.getId()).isEqualTo(findByEmailUser.getId());
        assertThat(savedUser.getId()).isEqualTo(byEmailAndPassword.getId());
    }



}
