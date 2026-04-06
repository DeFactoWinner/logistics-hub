package com.winner.client.userservice.user.presentation.dto.response;

import com.winner.client.userservice.user.application.dto.result.UserSearchResult;
import com.winner.client.userservice.user.domain.enums.ApprovalStatusType;
import com.winner.client.userservice.user.domain.enums.UserStatusType;
import com.winner.client.userservice.user.domain.vo.UserRole;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserSearchResponse(
    UUID userId,
    String name,
    String slackId,
    UserStatusType userStatus,
    ApprovalStatusType approvalStatus,
    UserRole userRole,
    LocalDateTime createAt
) {

  public static UserSearchResponse from(UserSearchResult searchResult) {
    return UserSearchResponse.builder()
        .userId(searchResult.userId())
        .name(searchResult.name())
        .slackId(searchResult.slackId())
        .approvalStatus(searchResult.approvalStatus())
        .userStatus(searchResult.userStatus())
        .userRole(searchResult.userRole())
        .createAt(searchResult.createAt())
        .build();
  }
}
