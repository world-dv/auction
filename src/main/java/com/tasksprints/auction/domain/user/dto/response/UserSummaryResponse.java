package com.tasksprints.auction.domain.user.dto.response;

import com.tasksprints.auction.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserSummaryResponse {
    private Long id;
    private String nickName;
    private String name;
    private String email;

    public UserSummaryResponse(User user) {
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
