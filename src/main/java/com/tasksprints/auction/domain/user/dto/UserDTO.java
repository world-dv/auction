package com.tasksprints.auction.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String nickName;
}
