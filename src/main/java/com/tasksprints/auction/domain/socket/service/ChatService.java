package com.tasksprints.auction.domain.socket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasksprints.auction.domain.socket.dto.AddChatRoomDto;
import com.tasksprints.auction.domain.socket.model.ChatRoom;
import com.tasksprints.auction.domain.socket.repository.ChatRoomRepository;
import com.tasksprints.auction.domain.user.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper mapper;
    private ConcurrentHashMap<String, ChatRoom> chatRoomMap;
    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    private void init() {
        chatRoomMap = new ConcurrentHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRoomMap.values());
    }

    public ChatRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    public User findOwnerById(String id) {
        return findRoomById(id).getOwner();
    }

    public boolean isUserOwner(String id, Long user) {
        return findOwnerById(id).getId().equals(user);
    }

    @Transactional
    public void createRoom(AddChatRoomDto addChatRoomDto) {
        ChatRoom chatRoom = chatRoomRepository.save(addChatRoomDto.toEntity());
        log.info("Create Room : {} {}", chatRoom.getId(), chatRoom.getName());
        chatRoomMap.put(chatRoom.getChatRoomId(), chatRoom);
    }
}
