package com.tasksprints.auction.domain.user.model;

import com.tasksprints.auction.common.BaseEntityWithUpdate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted_at is null")
@Getter
@ToString
@Entity(name = "users")
public class User extends BaseEntityWithUpdate {
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

    @Column(nullable = true)
    private LocalDateTime deletedAt;

    /**
     * @description
     * 인자 길이가 너무 길어서, 함수단위로 쪼갤지에 대한 고민중
     */
    public void update(String name, String password, String nickName){
        this.name = name;
        this.password = password;
        this.nickName = nickName;
    }

    /**
     * @descripton
     * static factory pattern을 적용하여, 구현
     */
    public static User create(String name, String email, String password, String nickName){
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .nickName(nickName)
                .build();
    }

    public void delete(){
        this.deletedAt = LocalDateTime.now();
    }
}
