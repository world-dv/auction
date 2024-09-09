package com.tasksprints.auction.domain.user;

import com.tasksprints.auction.domain.user.dto.response.UserDetailResponse;
import com.tasksprints.auction.domain.user.dto.request.UserRequest;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import com.tasksprints.auction.domain.user.service.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;


import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User existingUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        existingUser = User.builder()
                .id(1L)
                .name("testUser")
                .nickName("testNick")
                .password("testPassword")
                .email("test@example.com")
                .build();
    }

    @Nested
    @DisplayName("Create User")
    class CreateUserTests {

        @Test
        @DisplayName("should create a new user")
        void shouldCreateNewUser() {
            // Arrange
            UserRequest.Register request = new UserRequest.Register("testUser", "test@example.com", "testPassword", "testNick");
            when(userRepository.save(any(User.class))).thenReturn(existingUser);

            // Act
            UserDetailResponse createdUser = userService.createUser(request);

            // Assert
            Assertions.assertNotNull(createdUser);
            Assertions.assertEquals("testUser", createdUser.getName());
            Assertions.assertEquals("test@example.com", createdUser.getEmail());
            Assertions.assertEquals("testNick", createdUser.getNickName());
            verify(userRepository, times(1)).save(any(User.class));
        }
    }

    @Nested
    @DisplayName("Get User By ID")
    class GetUserByIdTests {

        @Test
        @DisplayName("should return user when found")
        void shouldReturnUserWhenFound() {
            // Arrange
            when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

            // Act
            UserDetailResponse user = userService.getUserDetailsById(1L);

            // Assert
            Assertions.assertNotNull(user);
            Assertions.assertEquals(existingUser.getId(), user.getId());
            Assertions.assertEquals(existingUser.getName(), user.getName());
            verify(userRepository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("should throw exception when user not found")
        void shouldThrowExceptionWhenUserNotFound() {
            // Arrange
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert
            Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserDetailsById(1L));
            verify(userRepository, times(1)).findById(1L);
        }
    }

    @Nested
    @DisplayName("Update User")
    class UpdateUserTests {

        @Test
        @DisplayName("should update user details")
        void shouldUpdateUserDetails() {
            // Arrange
            UserRequest.Update request = new UserRequest.Update("updatedName", "updatedPassword", "updatedNick");
            when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
            when(userRepository.save(any(User.class))).thenReturn(existingUser);

            // Act
            UserDetailResponse updatedUser = userService.updateUser(1L, request);

            // Assert
            Assertions.assertNotNull(updatedUser);
            Assertions.assertEquals("updatedName", updatedUser.getName());
            Assertions.assertEquals("updatedPassword", updatedUser.getPassword());
            Assertions.assertEquals("updatedNick", updatedUser.getNickName());
            verify(userRepository, times(1)).findById(1L);
            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        @DisplayName("should throw exception when user not found")
        void shouldThrowExceptionWhenUserNotFound() {
            // Arrange
            UserRequest.Update request = new UserRequest.Update("updatedName", null, null);
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert
            Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, request));
            verify(userRepository, times(1)).findById(1L);
            verify(userRepository, never()).save(any(User.class));
        }
    }

    @Nested
    @DisplayName("Delete User")
    class DeleteUserTests {

        @Test
        @DisplayName("should delete user when found")
        void shouldDeleteUserWhenFound() {
            // Arrange
            User existingUser = User.builder()
                    .id(1L)
                    .name("testUser")
                    .nickName("testNick")
                    .password("testPassword")
                    .email("test@example.com")
                    .build();
            when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
            when(userRepository.save(any(User.class))).thenReturn(existingUser);

            // Act
            userService.deleteUser(1L);

            // Assert
            verify(userRepository, times(1)).findById(1L);
            verify(userRepository, times(1)).save(existingUser);

            // Verify that the user's deletedAt field was updated correctly
            Assertions.assertNotNull(existingUser.getDeletedAt());
        }

        @Test
        @DisplayName("should throw exception when user not found")
        void shouldThrowExceptionWhenUserNotFound() {
            // Arrange
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert
            Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
            verify(userRepository, times(1)).findById(1L);
            verify(userRepository, never()).delete(any(User.class));
        }
    }
}