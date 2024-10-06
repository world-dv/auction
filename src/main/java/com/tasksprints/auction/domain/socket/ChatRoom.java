package com.tasksprints.auction.domain.socket;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "chatRoomId")
    private String chatRoomId;

    @Column(name = "name")
    private String name;

    @OneToMany
    @Column(name = "chatterList")
    private List<Chatter> chatters;

    @Builder
    public ChatRoom(String name) {
        this.chatRoomId = UUID.randomUUID().toString();
        this.name = name;
        this.chatters = new ArrayList<>();
    }
}
