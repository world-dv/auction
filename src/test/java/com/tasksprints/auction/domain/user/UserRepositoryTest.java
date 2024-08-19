package com.tasksprints.auction.domain.user;

import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

/**
 * [CRUD TEST] UserRepositoryTest
 */
@DataJpaTest
@Slf4j
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("testUser")
                .nickName("testNick")
                .password("testPassword")
                .email("test@example.com")
                .build();
    }
    @DisplayName("findById 테스트")
    @Test
    void findUserById() {
        User createdUser = userRepository.save(user);
        User foundUser = userRepository.findById(createdUser.getId()).orElse(null);

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(createdUser.getId(), foundUser.getId());
        Assertions.assertEquals("testUser", foundUser.getName());
        Assertions.assertEquals("testNick", foundUser.getNickName());
        Assertions.assertEquals("test@example.com", foundUser.getEmail());

        log.info("Found User: {}", foundUser);
    }

    @DisplayName("save 테스트")
    @Nested
    class SaveTest {
        @DisplayName("유저 업데이트 테스트")
        @Test
        void updateUser() {
            User createdUser = userRepository.save(user);
            createdUser.update("updatedName", "updatedPassword", "updatedNickName");
            User updatedUser = userRepository.save(createdUser);

            Assertions.assertNotNull(updatedUser);
            Assertions.assertEquals("updatedName", updatedUser.getName());
            Assertions.assertEquals("updatedNickName", updatedUser.getNickName());
            Assertions.assertEquals("updatedPassword", updatedUser.getPassword());

            log.info("Updated User: {}", updatedUser);
        }
        @DisplayName("유저 생성 테스트")
        @Test
        void createUser() {
            User createdUser = userRepository.save(user);

            Assertions.assertNotNull(createdUser);
            Assertions.assertNotNull(createdUser.getId());
            Assertions.assertEquals("testUser", createdUser.getName());
            Assertions.assertEquals("testNick", createdUser.getNickName());
            Assertions.assertEquals("test@example.com", createdUser.getEmail());

            log.info("Created User: {}", createdUser);
        }
    }
    @DisplayName("delete 테스트")
    @Test
    void deleteUser() {
        User createdUser = userRepository.save(user);
        Long userId = createdUser.getId();

        userRepository.deleteById(userId);

        Optional<User> deletedUser = userRepository.findById(userId);

        Assertions.assertTrue(deletedUser.isEmpty(), "User should be deleted");
        log.info("User with ID {} deleted", userId);
    }
}