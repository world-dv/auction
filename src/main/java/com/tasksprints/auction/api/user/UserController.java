package com.tasksprints.auction.api.user;

import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.common.response.ApiResult;
import com.tasksprints.auction.domain.user.dto.response.UserDetailResponse;
import com.tasksprints.auction.domain.user.dto.request.UserRequest;
import com.tasksprints.auction.domain.user.dto.response.UserSummaryResponse;
import com.tasksprints.auction.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Register User", description = "Register a new user with the provided user information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid user data.")
    })
    @PostMapping()
    public ResponseEntity<ApiResult<UserDetailResponse>> register(@RequestBody UserRequest.Register user) {
        UserDetailResponse createdUser = userService.createUser(user);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.USER_CREATED_SUCCESS, createdUser));
    }

    @Operation(summary = "Get User by ID", description = "Retrieve user details based on the user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<UserDetailResponse>> getUserById(@PathVariable Long id) {
        UserDetailResponse user = userService.getUserDetailsById(id);
        return ResponseEntity.ok(ApiResult.success(null, user));
    }

    @Operation(summary = "Get All Users", description = "Retrieve a summary list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User summary list retrieved successfully."),
            @ApiResponse(responseCode = "204", description = "No users found.")
    })
    @GetMapping()
    public ResponseEntity<ApiResult<List<UserSummaryResponse>>> getAllUsers() {
        List<UserSummaryResponse> users = userService.getUsersSummary();
        return ResponseEntity.ok(ApiResult.success(null, users));
    }

    @Operation(summary = "Update User", description = "Update user information for a specific user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @PutMapping()
    public ResponseEntity<ApiResult<UserDetailResponse>> updateUser(@RequestParam Long id, @RequestBody UserRequest.Update user) {
        UserDetailResponse updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.USER_UPDATED_SUCCESS, updatedUser));
    }

    @Operation(summary = "Delete User", description = "Delete a user by user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @DeleteMapping()
    public ResponseEntity<ApiResult<String>> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.USER_DELETED_SUCCESS));
    }
//    @GetMapping("/reviews")
//    @Operation(summary = "Get reviews by user", description = "Retrieves all reviews created by a specific user.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "User reviews retrieved successfully"),
//            @ApiResponse(responseCode = "404", description = "User not found")
//    })
//    public ResponseEntity<ApiResult<List<ReviewDTO>>> getReviewsByUser(@RequestParam Long userId) {
//        List<ReviewDTO> reviews = reviewService.getReviewsByUserId(userId);
//        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.REVIEWS_RETRIEVED, reviews));
//    }

}
