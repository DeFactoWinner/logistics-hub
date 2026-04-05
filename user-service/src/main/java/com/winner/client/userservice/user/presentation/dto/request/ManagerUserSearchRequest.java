package com.winner.client.userservice.user.presentation.dto.request;

import com.winner.client.global.pagination.CommonPageRequest;
import com.winner.client.global.pagination.PageSortType;
import com.winner.client.userservice.user.application.dto.query.ManagerUserPageQuery;
import com.winner.client.userservice.user.domain.enums.RoleType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ManagerUserSearchRequest(
    RoleType role
) {

  public static ManagerUserPageQuery toQuery
      (ManagerUserSearchRequest searchRequest,
          CommonPageRequest pageRequest, UUID referenceId) {
    return ManagerUserPageQuery.builder()
        .role(searchRequest.role())
        .referenceId(referenceId)
        .sortType(PageSortType.valueOf(pageRequest.getSort()))
        .page(pageRequest.getPage())
        .size(pageRequest.getSize())
        .build();
  }
}
