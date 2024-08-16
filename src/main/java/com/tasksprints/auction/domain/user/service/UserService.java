package com.tasksprints.auction.domain.user.service;

import com.tasksprints.auction.domain.user.dto.UserRequest;
import com.tasksprints.auction.domain.user.model.User;

public interface UserService {
    User createUser(UserRequest.Register user);
    User getUserById(Long id);
    User updateUser(Long id, UserRequest.Update user);
    void deleteUser(Long id);
}
