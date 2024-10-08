package com.tasksprints.auction.domain.socket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatter {

    @Id
    @Column(name = "chatterId")
    private String chatterId;

    @Column(name = "sessionId")
    private String sessionId;

    @Builder
    public Chatter(String id, String sessionId) {
        this.chatterId = id;
        this.sessionId = sessionId;
    }
}
