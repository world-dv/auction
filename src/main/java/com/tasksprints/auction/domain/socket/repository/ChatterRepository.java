package com.tasksprints.auction.domain.socket.repository;

import com.tasksprints.auction.domain.socket.model.Chatter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatterRepository extends JpaRepository<Chatter, String> {
}
g
