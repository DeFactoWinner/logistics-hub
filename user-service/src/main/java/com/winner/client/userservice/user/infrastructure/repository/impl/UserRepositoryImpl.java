package com.winner.client.userservice.user.infrastructure.repository.impl;

import com.winner.client.userservice.user.application.dto.query.AdminUserPageQuery;
import com.winner.client.userservice.user.application.dto.query.ManagerUserPageQuery;
import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.domain.repository.UserRepository;
import com.winner.client.userservice.user.infrastructure.repository.JpaUserRepository;
import com.winner.client.userservice.user.infrastructure.repository.UserQueryRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private final JpaUserRepository jpaUserRepository;
  private final UserQueryRepository userQueryRepository;

  public UserRepositoryImpl(JpaUserRepository jpaUserRepository,
      UserQueryRepository userQueryRepository) {
    this.jpaUserRepository = jpaUserRepository;
    this.userQueryRepository = userQueryRepository;
  }

  @Override
  public User save(User user) {
    return jpaUserRepository.save(user);
  }

  @Override
  public boolean existsByUserNameAndDeletedAtNull(String username) {
    return jpaUserRepository.existsByUserNameAndDeletedAtNull(username);
  }

  @Override
  public Optional<User> findByIdAndDeletedAtNull(UUID userId) {
    return jpaUserRepository.findByIdAndDeletedAtNull(userId);
  }

  @Override
  public Optional<User> findByUserNameAndDeletedAtNull(String username) {
    return jpaUserRepository.findByUserNameAndDeletedAtNull(username);
  }

  @Override
  public Page<User> findAllByManagerScope(ManagerUserPageQuery query) {
    return userQueryRepository.findAllByManagerScope(query);
  }

  @Override
  public Page<User> findAllByAdminCondition(AdminUserPageQuery query) {
    return userQueryRepository.findAllByAdminCondition(query);
  }
}
