package com.tasksprints.auction.domain.socket.dto;

import com.tasksprints.auction.domain.socket.model.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddChatRoomDto {

    private String name;

    public ChatRoom toEntity() {
        return ChatRoom.builder()
            .name(name)
            .build();
    }
}
