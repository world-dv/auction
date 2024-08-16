package com.tasksprints.auction.domain.user.service;

import com.tasksprints.auction.domain.user.dto.UserRequest;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(UserRequest.Register request) {
        // Validate request if needed
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .nickName(request.getNickname())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    @Override
    public User updateUser(Long id, UserRequest.Update request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        user.update(request.getName(), request.getPassword(), request.getNickname());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
        userRepository.delete(user);
    }
}