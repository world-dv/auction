package com.tasksprints.auction.domain.user.repository;

import com.tasksprints.auction.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
