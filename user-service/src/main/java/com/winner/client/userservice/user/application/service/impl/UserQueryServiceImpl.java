package com.winner.client.userservice.user.application.service.impl;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.userservice.common.exception.UserErrorCode;
import com.winner.client.userservice.user.application.result.UserDetailResult;
import com.winner.client.userservice.user.application.service.UserQueryService;
import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.domain.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

  private final UserRepository userRepository;

  public UserDetailResult getUserDetail(UUID userId) {
    User user = userRepository.findByIdAndDeletedAtNull(userId)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    return UserDetailResult.from(user);
  }
}
