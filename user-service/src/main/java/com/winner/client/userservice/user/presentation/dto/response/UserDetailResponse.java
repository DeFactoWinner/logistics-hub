package com.winner.client.userservice.user.presentation.dto.response;

import com.winner.client.userservice.user.application.dto.result.UserDetailResult;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserDetailResponse(
    UUID userId,
    String userName,
    String name,
    String phoneNumber,
    String approvalStatus,
    String userStatus,
    String userRole,
    UUID referenceId,
    String slackId,
    LocalDateTime updatedAt
) {

  public static UserDetailResponse from(UserDetailResult command) {

    return UserDetailResponse.builder()
        .userId(command.userId())
        .userName(command.userName())
        .name(command.name())
        .phoneNumber(command.phoneNumber().getNumber())
        .approvalStatus(command.approvalStatus().name())
        .userStatus(command.userStatus().name())
        .userRole(command.role())
        .referenceId(command.referenceId())
        .slackId(command.slackId())
        .updatedAt(command.updatedAt())
        .build();
  }
}