package com.tasksprints.auction.domain.socket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
import com.tasksprints.auction.domain.socket.dto.AddChatRoomDto;
import com.tasksprints.auction.domain.socket.model.ChatRoom;
import com.tasksprints.auction.domain.socket.repository.ChatRoomRepository;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
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
public class ChatServiceImpl implements ChatService {

    private final ObjectMapper mapper;
    private ConcurrentHashMap<String, ChatRoom> chatRoomMap;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

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

    @Override
    @Transactional
    public void createRoom(Long userId, Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
            .orElseThrow(() -> new IllegalArgumentException("NOT FOUND : auction " + auctionId));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("NOT FOUND : user " + userId));
        AddChatRoomDto addChatRoomDto = new AddChatRoomDto(auction.getProduct().getName(), user);
        ChatRoom chatRoom = chatRoomRepository.save(addChatRoomDto.toEntity());
        log.info("Create Room : {} {}", chatRoom.getId(), chatRoom.getName());
        chatRoomMap.put(chatRoom.getChatRoomId(), chatRoom);
    }
}
