package com.winner.client.userservice.user.application.service;

import com.winner.client.userservice.user.application.dto.command.UserPatchCommand;
import com.winner.client.userservice.user.application.dto.result.UserDetailResult;
import java.util.UUID;

public interface UserCommandService {

  Void logout(UUID userId, String accessToken);

  UserDetailResult updateUser(UUID uuid, UserPatchCommand command);
}
