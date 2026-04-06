package com.winner.client.userservice.user.application.dto.query;

import com.winner.client.global.pagination.PageSortType;
import com.winner.client.userservice.user.domain.enums.ApprovalStatusType;
import com.winner.client.userservice.user.domain.enums.RoleType;
import com.winner.client.userservice.user.domain.enums.UserStatusType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AdminUserPageQuery(
    RoleType role,
    UUID referenceId,
    UserStatusType userStatus,
    ApprovalStatusType approvalStatus,
    PageSortType sortType,
    int page,
    int size
) {


}
