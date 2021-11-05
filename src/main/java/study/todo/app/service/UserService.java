package study.todo.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.todo.app.domain.UserEntity;
import study.todo.app.repository.UserRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity) {
        validate(userEntity);
        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String email, final String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    private void validate(UserEntity userEntity) {
        if (userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String email = userEntity.getEmail();
        if (userRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }
    }


}
