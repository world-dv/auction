package com.tasksprints.auction.domain.socket.repository;

import com.tasksprints.auction.domain.socket.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
