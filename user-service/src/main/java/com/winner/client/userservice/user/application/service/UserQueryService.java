package com.winner.client.userservice.user.application.service;

import com.winner.client.userservice.user.application.dto.query.AdminUserPageQuery;
import com.winner.client.userservice.user.application.dto.query.ManagerUserPageQuery;
import com.winner.client.userservice.user.application.dto.result.UserDetailResult;
import com.winner.client.userservice.user.application.dto.result.UserSearchResult;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface UserQueryService {

  UserDetailResult getUserDetail(UUID userId);

  Page<UserSearchResult> queryUsersByManage(ManagerUserPageQuery query);

  Page<UserSearchResult> queryUsersByAdmin(AdminUserPageQuery query);
}
