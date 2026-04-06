package com.winner.client.userservice.user.application.service.impl;

import com.winner.client.global.config.jwt.JwtTokenProvider;
import com.winner.client.global.exception.BusinessException;
import com.winner.client.userservice.common.exception.UserErrorCode;
import com.winner.client.userservice.user.application.dto.command.UnAssignManagersCommand;
import com.winner.client.userservice.user.application.dto.command.UserPatchCommand;
import com.winner.client.userservice.user.application.dto.result.UserDetailResult;
import com.winner.client.userservice.user.application.port.DeliverManagerPort;
import com.winner.client.userservice.user.application.service.UserCommandService;
import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.domain.enums.RoleType;
import com.winner.client.userservice.user.domain.repository.TokenRepository;
import com.winner.client.userservice.user.domain.repository.UserRepository;
import com.winner.client.userservice.user.domain.vo.PhoneNumber;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

  private final TokenRepository tokenRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;
  private final DeliverManagerPort deliverManagerPort;

  @Override
  public Void logout(UUID userId, String accessToken) {
    tokenRepository.deleteRefreshToken(userId);
    long expiresIn = jwtTokenProvider.getRemainingTime(accessToken);
    tokenRepository.registerBlacklist(accessToken, expiresIn);
    return null;
  }

  @Override
  public UserDetailResult updateUser(UserPatchCommand command) {
    User user = userRepository.findByIdAndDeletedAtNull(command.userId())
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    user.updateProfile(
        command.name(),
        command.phoneNumber() != null
            ? new PhoneNumber(command.phoneNumber()) : null,
        command.slackId()
    );
    return UserDetailResult.from(user);
  }

  @Override
  public Void unAssignManagersFromAdmin(UnAssignManagersCommand command) {
    List<User> users = userRepository.findAllByReferenceId(command.referenceId());
    users.forEach(User::suspend);
    return null;
  }

  @Override
  public Void deleteUser(UUID userId) {
    User user = userRepository.findByIdAndDeletedAtNull(userId)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    if (user.getUserRole().getRole() == RoleType.DELIVERY_MANAGER) {
      deliverManagerPort.deleteDeliveryManager(user);
    }
    user.delete();
    return null;
  }

  @Override
  public UserDetailResult approveUser(UUID userId) {
    User user = userRepository.findByIdAndDeletedAtNull(userId)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    if (user.isApprove()) {
      throw new BusinessException(UserErrorCode.ALREADY_USER_APPROVED);
    }
    user.approve();
    if (user.getUserRole().getRole() == RoleType.DELIVERY_MANAGER) {
      deliverManagerPort.registrationDeliveryManager(user);
    }
    user.active();

    return UserDetailResult.from(user);
  }

  @Override
  public Void rejectUser(UUID userId) {
    User user = userRepository.findByIdAndDeletedAtNull(userId)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    if (user.isApprove()) {
      throw new BusinessException(UserErrorCode.ALREADY_USER_APPROVED);
    }
    user.reject();
    return null;
  }
}
