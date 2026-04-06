package com.winner.client.userservice.user.infrastructure.repository;

import com.winner.client.userservice.user.application.dto.query.AdminUserPageQuery;
import com.winner.client.userservice.user.application.dto.query.ManagerUserPageQuery;
import com.winner.client.userservice.user.domain.entity.User;
import org.springframework.data.domain.Page;

public interface UserQueryRepository {

  Page<User> findAllByManagerScope(ManagerUserPageQuery query);

  Page<User> findAllByAdminCondition(AdminUserPageQuery query);
}