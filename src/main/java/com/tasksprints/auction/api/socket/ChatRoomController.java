package com.tasksprints.auction.api.socket;

import com.tasksprints.auction.domain.socket.dto.AddChatRoomDto;
import com.tasksprints.auction.domain.socket.model.ChatRoom;
import com.tasksprints.auction.domain.socket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatRoomController {

    private final ChatService chatService;

    @GetMapping("/chat/room/all")
    @ResponseBody
    public List<ChatRoom> chatRoomAll() {
        return chatService.findAllRoom();
    } //채팅방 목록 조회

    @PostMapping("/chat/room")
    @ResponseBody
    public ChatRoom createChatRoom(@RequestParam("roomInfo") AddChatRoomDto addChatRoomDto) {
        return chatService.createRoom(addChatRoomDto);
    } //채팅방 생성

    @GetMapping("/chat/room/{chatRoomId}")
    @ResponseBody
    public ChatRoom chatRoom(@PathVariable(value = "chatRoomId") String chatRoomId) {
        return chatService.findRoomById(chatRoomId);
    } //채팅방 조회
}
