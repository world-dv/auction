package com.tasksprints.auction.domain.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WhisperDto {

    private String roomId;
    private Long sender;
    private Long receiver;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
