package com.winner.client.userservice.user.application.service;

import com.winner.client.userservice.user.application.result.UserDetailResult;
import java.util.UUID;

public interface UserQueryService {

  UserDetailResult getUserDetail(UUID userId);
}
