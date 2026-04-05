package com.winner.client.userservice.user.application.service;

import java.util.UUID;

public interface UserCommandService {

  Void logout(UUID userId, String accessToken);
}
