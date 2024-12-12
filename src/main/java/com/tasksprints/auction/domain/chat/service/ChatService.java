package com.tasksprints.auction.domain.chat.service;

import com.tasksprints.auction.domain.chat.dto.AddChatRoomDto;
import com.tasksprints.auction.domain.chat.model.ChatRoom;
import com.tasksprints.auction.domain.user.model.User;
import java.util.List;

public interface ChatService {

    List<ChatRoom> findAllRoom();

    ChatRoom findRoomById(String id);

    User findOwnerById(String id);

    boolean isUserOwner(String id, Long user);

    void createRoom(AddChatRoomDto addChatRoomDto);
}
