package com.winner.client.userservice.user.domain.repository;

import com.winner.client.userservice.user.application.dto.query.AdminUserPageQuery;
import com.winner.client.userservice.user.application.dto.query.ManagerUserPageQuery;
import com.winner.client.userservice.user.domain.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface UserRepository {

  User save(User user);

  boolean existsByUserNameAndDeletedAtNull(String username);

  Optional<User> findByIdAndDeletedAtNull(UUID userId);

  Optional<User> findByUserNameAndDeletedAtNull(String username);

  Page<User> findAllByManagerScope(ManagerUserPageQuery query);

  Page<User> findAllByAdminCondition(AdminUserPageQuery query);
}