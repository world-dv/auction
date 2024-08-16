package com.tasksprints.auction.domain.user.dto;

import com.tasksprints.auction.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDetailsDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String nickName;
    private UserDetailsDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickName = user.getNickName();
    }
    public static UserDetailsDTO of(User user){
        return new UserDetailsDTO(user);
    }

}
