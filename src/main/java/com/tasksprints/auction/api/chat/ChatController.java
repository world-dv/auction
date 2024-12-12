package com.tasksprints.auction.api.chat;

import com.tasksprints.auction.domain.chat.dto.MessageDto;
import com.tasksprints.auction.domain.chat.dto.MessageDto.MessageType;
import com.tasksprints.auction.domain.chat.dto.WhisperDto;
import com.tasksprints.auction.domain.chat.service.ChatService;
import com.tasksprints.auction.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final UserService userService;
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(MessageDto messageDto) {
        if (chatService.isUserOwner(messageDto.getRoomId(), messageDto.getSender())) {
            return;
        } //메시지 전송자가 경매자라면 메시지 전송 금지
        String sender = userService.getUserDetailsById(messageDto.getSender()).getNickName();

        if (MessageType.ENTER.equals(messageDto.getType())) {
            messageDto.setMessage(sender + "님이 입장하셨습니다.");
        }
        if (MessageType.LEAVE.equals(messageDto.getType())) {
            messageDto.setMessage(sender + "님이 퇴장하셨습니다.");
        }
        simpMessageSendingOperations.convertAndSend("/topic/chat/room/" + messageDto.getRoomId(), messageDto);
    }

    @MessageMapping("/chat/message/whisper")
    public void messageToOne(WhisperDto whisperDto) {
        if (chatService.isUserOwner(whisperDto.getRoomId(), whisperDto.getSender())) {
            return;
        } //메시지 전송자가 경매자라면 메시지 전송 금지

        String sender = userService.getUserDetailsById(whisperDto.getSender()).getNickName();
        whisperDto.setMessage("[귓속말] " + sender + " : " + whisperDto.getMessage());
        simpMessageSendingOperations.convertAndSend("/whisper/" + whisperDto.getReceiver(), whisperDto);
        //유저 생성 시 "/whisper/{유저 email 또는 nickname}" 경로를 구독해야 귓속말이 가능
        //유저가 보낼 때 받는 사람의 경로로 메시지를 전달 -> 받는 사람만 메시지를 볼 수 있음
        //답장 또한 받은 사람이 보낼 사람의 경로로 메시지를 전달해 1대 1 채팅이 되도록 함
    }
}