package com.winner.client.userservice.user.domain.repository;

import com.winner.client.userservice.user.domain.entity.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

  User save(User user);

  boolean existsByUsernameAndDeletedAtNull(String username);

  Optional<User> findByIdAndDeletedAtNull(UUID userId);

  Optional<User> findByUsernameAndDeletedAtNull(String username);
}