package com.winner.client.userservice.user.application.dto.result;

import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.domain.enums.ApprovalStatusType;
import com.winner.client.userservice.user.domain.enums.UserStatusType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SignupResult(
    UUID userId,
    UserStatusType userStatusType,
    ApprovalStatusType approvalStatusType,
    LocalDateTime createdAt
) {

  public static SignupResult from(User user) {
    return SignupResult.builder()
        .userId(user.getId())
        .userStatusType(user.getUserStatus())
        .approvalStatusType(user.getApprovalStatus())
        .createdAt(user.getCreatedAt()).build();
  }
}