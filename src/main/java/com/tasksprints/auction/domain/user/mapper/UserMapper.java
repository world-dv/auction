package com.tasksprints.auction.domain.user.mapper;

import com.tasksprints.auction.domain.user.dto.UserDTO;
import com.tasksprints.auction.domain.user.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getNickName()
        );
    }

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .nickName(userDTO.getNickName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .nickName(userDTO.getNickName())
                .build();
    }
}
