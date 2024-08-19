package com.tasksprints.auction.domain.user.service;

import com.tasksprints.auction.domain.user.dto.UserDetailsDTO;
import com.tasksprints.auction.domain.user.dto.UserRequest;
import com.tasksprints.auction.domain.user.dto.UserSummaryDTO;
import com.tasksprints.auction.domain.user.model.User;

import java.util.List;

public interface UserService {
    UserDetailsDTO createUser(UserRequest.Register user);
    UserDetailsDTO getUserDetailsById(Long id);
    List<UserSummaryDTO> getUsersSummary();
    UserDetailsDTO updateUser(Long id, UserRequest.Update user);
    void deleteUser(Long id);
}
