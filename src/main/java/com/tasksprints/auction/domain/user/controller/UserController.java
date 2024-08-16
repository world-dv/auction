package com.tasksprints.auction.domain.user.controller;

import com.tasksprints.auction.domain.user.dto.UserRequest;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     *
     * @return User
     * 이부분은 추후에 Response DTO로 교체할 예정
     */
    @PostMapping()
    User register(@RequestBody UserRequest.Register user){
        return userService.createUser(user);
    }


    @GetMapping()
    User getUserById(@RequestParam Long id){
        return userService.getUserById(id);
    }


    @PutMapping()
    User updateUser(@RequestParam Long id, @RequestBody UserRequest.Update user){
        return userService.updateUser(id, user);
    }

    @DeleteMapping()
    String deleteUser(@RequestParam Long id){
        userService.deleteUser(id);
        return "good";
    }

}
