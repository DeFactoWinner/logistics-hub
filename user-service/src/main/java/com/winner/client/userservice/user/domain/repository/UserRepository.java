package com.winner.client.userservice.user.domain.repository;

import com.winner.client.userservice.user.domain.entity.User;
import java.util.Optional;

public interface UserRepository {

  User save(User user);

  boolean existsByUsernameAndDeletedAtNull(String username);

  Optional<User> findByUsernameAndDeletedAtNull(String username);
}