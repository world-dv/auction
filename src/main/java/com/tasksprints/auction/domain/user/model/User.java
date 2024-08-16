package com.tasksprints.auction.domain.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    public void update(String name, String password, String nickName){
        this.name = name;
        this.password = password;
        this.nickName = nickName;
    }
}
