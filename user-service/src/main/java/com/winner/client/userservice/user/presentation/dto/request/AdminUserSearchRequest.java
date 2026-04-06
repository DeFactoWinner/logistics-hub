package com.winner.client.userservice.user.presentation.dto.request;

import com.winner.client.global.pagination.CommonPageRequest;
import com.winner.client.global.pagination.PageSortType;
import com.winner.client.userservice.user.application.dto.query.AdminUserPageQuery;
import com.winner.client.userservice.user.domain.enums.ApprovalStatusType;
import com.winner.client.userservice.user.domain.enums.RoleType;
import com.winner.client.userservice.user.domain.enums.UserStatusType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AdminUserSearchRequest(
    RoleType role,
    ApprovalStatusType approvalStatus,
    UserStatusType userStatus,
    UUID referenceId
) {

  public static AdminUserPageQuery toQuery(AdminUserSearchRequest request,
      CommonPageRequest pageRequest) {
    return AdminUserPageQuery.builder()
        .role(request.role)
        .userStatus(request.userStatus)
        .approvalStatus(request.approvalStatus)
        .referenceId(request.referenceId)
        .sortType(PageSortType.valueOf(pageRequest.getSort()))
        .page(pageRequest.getPage())
        .size(pageRequest.getSize())
        .build();
  }
}
