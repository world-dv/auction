package com.tasksprints.auction.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class UserRequest {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Register{
        String name;
        String email;
        String password;
        String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update{
        String name;
        String password;
        String nickname;
    }
}
