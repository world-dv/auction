package com.tasksprints.auction.api.user;

import com.tasksprints.auction.common.response.ApiResponse;
import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.domain.user.dto.UserDetailsDTO;
import com.tasksprints.auction.domain.user.dto.UserRequest;
import com.tasksprints.auction.domain.user.dto.UserSummaryDTO;
import com.tasksprints.auction.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ApiResponse<UserDetailsDTO>> register(@RequestBody UserRequest.Register user) {
        UserDetailsDTO createdUser = userService.createUser(user);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.USER_CREATED_SUCCESS, createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailsDTO>> getUserById(@PathVariable Long id) {
        UserDetailsDTO user = userService.getUserDetailsById(id);
        return ResponseEntity.ok(ApiResponse.success(null, user));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserSummaryDTO>>> getAllUsers() {
        List<UserSummaryDTO> users = userService.getUsersSummary();
        return ResponseEntity.ok(ApiResponse.success(null, users));
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<UserDetailsDTO>> updateUser(@RequestParam Long id, @RequestBody UserRequest.Update user) {
        UserDetailsDTO updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.USER_UPDATED_SUCCESS, updatedUser));
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<String>> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.USER_DELETED_SUCCESS));
    }
}
