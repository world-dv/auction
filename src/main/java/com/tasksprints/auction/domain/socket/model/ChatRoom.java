package com.tasksprints.auction.domain.socket.model;

import com.tasksprints.auction.domain.user.model.User;
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
    private String name; //채팅방 이름은 상품 이름으로 하면 좋을 것 같습니다.

    @Column(name = "owner")
    private User owner; //입찰자 -> 입찰 불가하도록 ? 설정 후 메시지 보내기 금지

    @OneToMany
    @Column(name = "users")
    private List<User> users;

    @Builder
    public ChatRoom(String name, User owner) {
        this.chatRoomId = UUID.randomUUID().toString();
        this.name = name;
        this.owner = owner;
        this.users = new ArrayList<>();
    }
}
