package com.tasksprints.auction.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasksprints.auction.domain.chat.dto.AddChatRoomDto;
import com.tasksprints.auction.domain.chat.model.ChatRoom;
import com.tasksprints.auction.domain.chat.repository.ChatRoomRepository;
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
public class ChatServiceImpl implements ChatService{

    private final ObjectMapper mapper;
    private ConcurrentHashMap<String, ChatRoom> chatRoomMap;
    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    private void init() {
        chatRoomMap = new ConcurrentHashMap<>();
    }

    @Override
    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRoomMap.values());
    }

    @Override
    public ChatRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    @Override
    public User findOwnerById(String id) {
        return findRoomById(id).getOwner();
    }

    @Override
    public boolean isUserOwner(String id, Long user) {
        return findOwnerById(id).getId().equals(user);
    }

    @Transactional
    @Override
    public void createRoom(AddChatRoomDto addChatRoomDto) {
        ChatRoom chatRoom = chatRoomRepository.save(addChatRoomDto.toEntity());
        log.info("Create Room : {} {}", chatRoom.getId(), chatRoom.getName());
        chatRoomMap.put(chatRoom.getChatRoomId(), chatRoom);
    }
}
