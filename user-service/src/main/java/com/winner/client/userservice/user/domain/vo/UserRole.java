package com.winner.client.userservice.user.domain.vo;

import com.winner.client.global.exception.BusinessException;
import com.winner.client.userservice.common.exception.UserErrorCode;
import com.winner.client.userservice.user.domain.enums.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
@Getter
@RequiredArgsConstructor
public class UserRole {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RoleType role;
  private UUID referenceId;

  public UserRole(RoleType roleType, UUID referenceId) {
    if ((roleType == RoleType.HUB_MANAGER || roleType == RoleType.COMPANY_MANAGER)
        && referenceId == null) {
      throw new BusinessException(UserErrorCode.MISSING_REFERENCE_ID);
    }
    this.role = roleType;
    this.referenceId = referenceId;
  }
}