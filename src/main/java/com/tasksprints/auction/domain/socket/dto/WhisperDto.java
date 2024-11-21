package com.tasksprints.auction.domain.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WhisperDto {

    private String sender; //보내는 사람 email 또는 닉네임
    private String receiver; //받는 사람 email 또는 닉네임
    private String message;
}

// email 은 unique 하기때문에 sender 와 receiver 로 사용 가능
// 하지만 nickname 을 고유하게 설정해서 nickname 을 사용해도 좋을 것 같음
