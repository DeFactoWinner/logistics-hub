package com.winner.client.userservice.user.application.dto.query;

import com.winner.client.global.pagination.PageSortType;
import com.winner.client.userservice.user.domain.enums.RoleType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ManagerUserPageQuery(
    RoleType role,
    UUID referenceId,
    PageSortType sortType,
    int page,
    int size
) {


}
