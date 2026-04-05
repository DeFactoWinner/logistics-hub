package com.winner.client.userservice.user.application.dto.result;

import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.domain.enums.ApprovalStatusType;
import com.winner.client.userservice.user.domain.enums.UserStatusType;
import com.winner.client.userservice.user.domain.vo.UserRole;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserSearchResult(
    UUID userId,
    String name,
    String slackId,
    UserStatusType userStatus,
    ApprovalStatusType approvalStatus,
    UserRole userRole,
    LocalDateTime createAt
) {

  public static UserSearchResult from(User user) {
    return UserSearchResult.builder()
        .userId(user.getId())
        .name(user.getName())
        .slackId(user.getSlackId())
        .approvalStatus(user.getApprovalStatus())
        .userStatus(user.getUserStatus())
        .userRole(user.getUserRole())
        .createAt(user.getCreatedAt())
        .build();
  }
}
