package com.tasksprints.auction.domain.socket.dto;

import com.tasksprints.auction.domain.socket.model.ChatRoom;
import com.tasksprints.auction.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddChatRoomDto {

    private String name;
    private User owner;

    public ChatRoom toEntity() {
        return new ChatRoom(name, owner);
    }
}
