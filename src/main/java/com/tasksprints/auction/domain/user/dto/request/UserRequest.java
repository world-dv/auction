package com.tasksprints.auction.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


public class UserRequest {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
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
