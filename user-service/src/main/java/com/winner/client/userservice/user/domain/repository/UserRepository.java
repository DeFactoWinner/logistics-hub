package com.winner.client.userservice.user.domain.repository;

import com.winner.client.userservice.user.domain.entity.User;

public interface UserRepository {

  User save(User user);

  boolean findByUsernameAndDeletedAtNull(String username);
}