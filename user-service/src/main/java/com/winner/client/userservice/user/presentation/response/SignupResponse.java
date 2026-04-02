package com.winner.client.userservice.user.presentation.response;

import com.winner.client.userservice.user.application.result.SignupResult;
import com.winner.client.userservice.user.domain.enums.ApprovalStatusType;
import com.winner.client.userservice.user.domain.enums.UserStatusType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SignupResponse(
    UUID userId,
    ApprovalStatusType approvalStatusType,
    UserStatusType userStatusType,
    LocalDateTime createdAt
) {

  public static SignupResponse from(SignupResult result) {
    return SignupResponse.builder()
        .userId(result.userId())
        .approvalStatusType(result.approvalStatusType())
        .userStatusType(result.userStatusType())
        .createdAt(result.createdAt())
        .build();
  }
}
