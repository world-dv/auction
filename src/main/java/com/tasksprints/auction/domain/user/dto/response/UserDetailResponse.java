package com.tasksprints.auction.domain.user.dto.response;

import com.tasksprints.auction.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDetailResponse {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String nickName;
    private UserDetailResponse(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickName = user.getNickName();
    }
    public static UserDetailResponse of(User user){
        return new UserDetailResponse(user);
    }

}
