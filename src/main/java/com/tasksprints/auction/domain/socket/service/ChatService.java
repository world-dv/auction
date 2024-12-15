package com.tasksprints.auction.domain.socket.service;

import com.tasksprints.auction.domain.socket.dto.AddChatRoomDto;
import com.tasksprints.auction.domain.socket.model.ChatRoom;
import com.tasksprints.auction.domain.user.model.User;

import java.util.List;

public interface ChatService {

    List<ChatRoom> findAllRoom();

    ChatRoom findRoomById(String id);

    User findOwnerById(String id);

    boolean isUserOwner(String id, Long user);

    ChatRoom createRoom(AddChatRoomDto addChatRoomDto);
}
