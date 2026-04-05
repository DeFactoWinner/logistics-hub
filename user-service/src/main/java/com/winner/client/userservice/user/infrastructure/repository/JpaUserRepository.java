package com.winner.client.userservice.user.infrastructure.repository;

import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.domain.repository.UserRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository
    extends UserRepository, JpaRepository<User, UUID> {

}
