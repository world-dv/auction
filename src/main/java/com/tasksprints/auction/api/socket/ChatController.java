package com.tasksprints.auction.api.socket;

import com.tasksprints.auction.domain.socket.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/chat/message")
    public void message(MessageDto messageDto) {
        if (MessageDto.MessageType.ENTER.equals(messageDto.getType())) {
            messageDto.setMessage(messageDto.getSender() + "님이 입장하셨습니다.");
        }

        if (MessageDto.MessageType.LEAVE.equals(messageDto.getType())) {
            messageDto.setMessage(messageDto.getSender() + "님이 퇴장하셨습니다.");
        }
        simpMessageSendingOperations.convertAndSend("/topic/chat/room/" + messageDto.getRoomId(), messageDto);
    }
}
