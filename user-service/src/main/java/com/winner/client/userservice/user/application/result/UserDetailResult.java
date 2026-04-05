package com.winner.client.userservice.user.application.result;

import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.domain.enums.ApprovalStatusType;
import com.winner.client.userservice.user.domain.enums.UserStatusType;
import com.winner.client.userservice.user.domain.vo.PhoneNumber;
import com.winner.client.userservice.user.domain.vo.UserRole;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserDetailResult(
    UUID userId,
    String userName,
    String name,
    PhoneNumber phoneNumber,
    String slackId,
    UserStatusType userStatus,
    ApprovalStatusType approvalStatus,
    UserRole userRole,
    LocalDateTime updatedAt
) {

  public static UserDetailResult from(User user) {
    return UserDetailResult.builder()
        .userId(user.getId())
        .userName(user.getUserName())
        .name(user.getName())
        .phoneNumber(user.getPhoneNumber())
        .slackId(user.getSlackId())
        .approvalStatus(user.getApprovalStatus())
        .userStatus(user.getUserStatus())
        .userRole(user.getUserRole())
        .updatedAt(user.getUpdatedAt())
        .build();
  }
}
