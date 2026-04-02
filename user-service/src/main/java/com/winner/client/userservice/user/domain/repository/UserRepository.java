package com.winner.client.userservice.user.domain.repository;

import com.winner.client.userservice.user.domain.entity.User;

public interface UserRepository {

  boolean existsByUsername(String username);

  User save(User user);
}