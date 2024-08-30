package com.tasksprints.auction.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.api.user.UserController;
import com.tasksprints.auction.domain.user.dto.response.UserDetailResponse;
import com.tasksprints.auction.domain.user.dto.request.UserRequest;
import com.tasksprints.auction.domain.user.dto.response.UserSummaryResponse;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import com.tasksprints.auction.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("성공적인 요청")
    class SuccessfulTests {

        @Test
        @DisplayName("POST /api/v1/user - 성공")
        void registerUser() throws Exception {
            UserDetailResponse userDetailResponse = new UserDetailResponse(1L, "John", "john@example.com", "password","john123");

            Mockito.when(userService.createUser(any(UserRequest.Register.class))).thenReturn(userDetailResponse);

            UserRequest.Register request = new UserRequest.Register("John", "john@example.com", "password", "john123");

            mockMvc.perform(post("/api/v1/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.message").value(ApiResponseMessages.USER_CREATED_SUCCESS))
                    .andExpect(jsonPath("$.data.name").value("John"))
                    .andExpect(jsonPath("$.data.email").value("john@example.com"));
        }

        @Test
        @DisplayName("GET /api/v1/user/{id} - 성공")
        void getUserById() throws Exception {
            UserDetailResponse userDetailResponse = new UserDetailResponse(1L, "John", "john@example.com", "password","john123");

            Mockito.when(userService.getUserDetailsById(anyLong())).thenReturn(userDetailResponse);

            mockMvc.perform(get("/api/v1/user/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data.name").value("John"))
                    .andExpect(jsonPath("$.data.email").value("john@example.com"));
        }

        @Test
        @DisplayName("GET /api/v1/user - 성공")
        void getAllUsers() throws Exception {
            UserSummaryResponse userSummaryResponse = new UserSummaryResponse(1L, "john123", "John", "john@example.com");

            List<UserSummaryResponse> users = Collections.singletonList(userSummaryResponse);

            Mockito.when(userService.getUsersSummary()).thenReturn(users);

            mockMvc.perform(get("/api/v1/user"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.data[0].name").value("John"))
                    .andExpect(jsonPath("$.data[0].email").value("john@example.com"));
        }

        @Test
        @DisplayName("PUT /api/v1/user - 성공")
        void updateUser() throws Exception {
            UserDetailResponse userDetailResponse = new UserDetailResponse(1L, "John Updated", "john@example.com", "newpassword", "john123updated");

            Mockito.when(userService.updateUser(anyLong(), any(UserRequest.Update.class))).thenReturn(userDetailResponse);

            UserRequest.Update request = new UserRequest.Update("John Updated", "newpassword", "john123updated");

            mockMvc.perform(put("/api/v1/user")
                            .param("id", "1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.message").value(ApiResponseMessages.USER_UPDATED_SUCCESS))
                    .andExpect(jsonPath("$.data.name").value("John Updated"));
        }

        @Test
        @DisplayName("DELETE /api/v1/user - 성공")
        void deleteUser() throws Exception {
            Mockito.doNothing().when(userService).deleteUser(anyLong());

            mockMvc.perform(delete("/api/v1/user")
                            .param("id", "1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.message").value(ApiResponseMessages.USER_DELETED_SUCCESS));
        }
    }

    @Nested
    @DisplayName("실패한 요청")
    class FailureTests {

        @Test
        @DisplayName("POST /api/v1/user - 실패")
        void registerUserFail() throws Exception {
            Mockito.when(userService.createUser(any(UserRequest.Register.class)))
                    .thenThrow(new RuntimeException("Failed to create user"));

            UserRequest.Register request = new UserRequest.Register("John", "john@example.com", "password", "john123");

            mockMvc.perform(post("/api/v1/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Failed to create user"));
        }

        @Test
        @DisplayName("GET /api/v1/user/{id} - 실패 (사용자 없음)")
        void getUserByIdFail() throws Exception {
            Mockito.when(userService.getUserDetailsById(anyLong()))
                    .thenThrow(new UserNotFoundException("User not found with id 1"));

            mockMvc.perform(get("/api/v1/user/{id}", 1L))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("User not found"));
        }

        @Test
        @DisplayName("GET /api/v1/user - 실패")
        void getAllUsersFail() throws Exception {
            Mockito.when(userService.getUsersSummary())
                    .thenThrow(new RuntimeException("Failed to retrieve users"));

            mockMvc.perform(get("/api/v1/user"))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Failed to retrieve users"));
        }

        @Test
        @DisplayName("PUT /api/v1/user - 실패")
        void updateUserFail() throws Exception {
            Mockito.when(userService.updateUser(anyLong(), any(UserRequest.Update.class)))
                    .thenThrow(new UserNotFoundException("User not found with id 1"));

            UserRequest.Update request = new UserRequest.Update("John Updated", "newpassword", "john123updated");

            mockMvc.perform(put("/api/v1/user")
                            .param("id", "1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("User not found"));
        }

        @Test
        @DisplayName("DELETE /api/v1/user - 실패 (사용자 없음)")
        void deleteUserFail() throws Exception {
            Mockito.doThrow(new UserNotFoundException("User not found with id 1"))
                    .when(userService).deleteUser(anyLong());

            mockMvc.perform(delete("/api/v1/user")
                            .param("id", "1"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("User not found"));
        }
    }
}
