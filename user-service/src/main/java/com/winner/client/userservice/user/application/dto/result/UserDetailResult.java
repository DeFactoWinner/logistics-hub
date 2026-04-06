package com.winner.client.userservice.user.application.dto.result;

import com.winner.client.userservice.user.domain.entity.User;
import com.winner.client.userservice.user.domain.enums.ApprovalStatusType;
import com.winner.client.userservice.user.domain.enums.UserStatusType;
import com.winner.client.userservice.user.domain.vo.PhoneNumber;
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
    String role,
    UUID referenceId,
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
        .role(user.getRoleName())
        .referenceId(user.getReferenceId())
        .updatedAt(user.getUpdatedAt())
        .build();
  }
}
