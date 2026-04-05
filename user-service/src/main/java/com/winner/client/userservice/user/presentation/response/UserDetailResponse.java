package com.winner.client.userservice.user.presentation.response;

import com.winner.client.userservice.user.application.result.UserDetailResult;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserDetailResponse(
    UUID userId,
    String username,
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
        .username(command.username())
        .name(command.name())
        .phoneNumber(command.phoneNumber().getNumber())
        .approvalStatus(command.approvalStatus().name())
        .userStatus(command.userStatus().name())
        .userRole(command.userRole().getRole().name())
        .referenceId(command.userRole().getReferenceId())
        .slackId(command.slackId())
        .updatedAt(command.updatedAt())
        .build();
  }
}