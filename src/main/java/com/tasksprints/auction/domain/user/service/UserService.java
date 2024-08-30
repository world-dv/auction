package com.tasksprints.auction.domain.user.service;

import com.tasksprints.auction.domain.user.dto.response.UserDetailResponse;
import com.tasksprints.auction.domain.user.dto.request.UserRequest;
import com.tasksprints.auction.domain.user.dto.response.UserSummaryResponse;

import java.util.List;

public interface UserService {
    UserDetailResponse createUser(UserRequest.Register user);
    UserDetailResponse getUserDetailsById(Long id);
    List<UserSummaryResponse> getUsersSummary();
    UserDetailResponse updateUser(Long id, UserRequest.Update user);
    void deleteUser(Long id);
}
