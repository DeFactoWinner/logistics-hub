package com.winner.client.userservice.user.infrastructure.repository;

import com.winner.client.userservice.user.domain.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository
    extends JpaRepository<User, UUID> {

  boolean existsByUserNameAndDeletedAtNull(String username);

  Optional<User> findByIdAndDeletedAtNull(UUID userId);

  Optional<User> findByUserNameAndDeletedAtNull(String username);

  List<User> findAllByUserRole_ReferenceId(UUID referenceId);
}
